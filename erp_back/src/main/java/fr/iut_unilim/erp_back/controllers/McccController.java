package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.McccRequest;
import fr.iut_unilim.erp_back.dto.McccResponse;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.model.CriticalConceptModel;
import fr.iut_unilim.erp_back.model.LearningRankModel;
import fr.iut_unilim.erp_back.model.SaeModel;
import fr.iut_unilim.erp_back.model.TeacherModel;
import fr.iut_unilim.erp_back.service.*;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static fr.iut_unilim.erp_back.tools.utils.RegexManipulation.getFirstRegexOccurence;

@RestController
@RequestMapping("/api/mccc")
public class McccController {

    private final McccService mcccService;
    private final CourseHoursService courseHoursService;
    private final ResourceService resourceService;
    private final TeacherService teacherService;
    private final SaeService saeService;
    private final SkillService skillService;
    private final RankService rankService;
    private final CriticalConceptService criticalConceptService;
    private final ConnectionService connectionService;

    public McccController(McccService mcccService, CourseHoursService courseHoursService, ResourceService resourceService, TeacherService teacherService, SaeService saeService, SkillService skillService, RankService rankService, CriticalConceptService criticalConceptService, ConnectionService connectionService) {
        this.mcccService = mcccService;
        this.courseHoursService = courseHoursService;
        this.resourceService = resourceService;
        this.teacherService = teacherService;
        this.saeService = saeService;
        this.skillService = skillService;
        this.rankService = rankService;
        this.criticalConceptService = criticalConceptService;
        this.connectionService = connectionService;
    }

    @GetMapping("/mcccs/{year}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getMccc(Authentication authentication, @PathVariable Integer year) {
        List<McccResponse> mcccResponses = mcccService.getAllMcccFromDepartmentAndYear(authentication.getName(), year)
                .stream()
                .map(McccResponse::new)
                .toList();
        return ResponseEntity.ok(mcccResponses);
    }

    @PostMapping("/save")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    @Transactional
    public ResponseEntity<?> saveMccc(@RequestBody McccRequest dto, Authentication authentication) throws ParseException {
        Optional<Resource> canHaveResource = resourceService.getResourceById(dto.getResourceID());
        if (canHaveResource.isEmpty()) {
            return ResponseEntity.badRequest().body("Le code de ressource n'existe pas !");
        }

        Mccc mccc = getCurrentMccc(dto, canHaveResource);
        handleDepartment(mccc, authentication);

        Set<Teacher> setTeacher = getTeachersFromDto(dto);
        mccc.setReferencialTeacherId(setTeacher);

        Set<Sae> setSae = getSAEFromDto(dto);
        if (mccc.getSaesId() == null) {
            mccc.setSaesId(new HashSet<>());
        }
        mccc.getSaesId().addAll(setSae);

        CourseHours courseHours = getCourseHoursFromDto(dto);
        mccc.setCourseHoursId(courseHours);

        getEditDateAndCreationDate(dto, mccc);


        Set<CriticalConcept> setCriticalConcepts = new HashSet<>();
        ResponseEntity<Object> doCriticalConceptsHasCrashed = fillCriticalConcepts(dto, setCriticalConcepts);
        if (doCriticalConceptsHasCrashed != null) return doCriticalConceptsHasCrashed;

        mccc.setCriticalConceptsId(setCriticalConcepts);
        mccc.setAcademicYearStart(dto.getYear());

        return ResponseEntity.ok("MCCC sauvegardée/mise à jour avec succès !");
    }


    private void getEditDateAndCreationDate(McccRequest dto, Mccc mccc) throws ParseException {
        mccc.setLastModificationDate(new SimpleDateFormat(("dd/MM/yyyy HH:mm:ss")).parse(dto.getEditDate()));
        mccc.setCreationDate(new SimpleDateFormat(("dd/MM/yyyy HH:mm:ss")).parse(dto.getCreationDate()));
    }


    @NotNull
    private Mccc getCurrentMccc(McccRequest dto, Optional<Resource> canHaveResource) {
        Mccc mccc;
        List<Mccc> allMcccs = mcccService.getAllMccc();
        Optional<Mccc> existingMccc = allMcccs.stream()
                .filter(m -> m.getResourceId().getResourceID().equals(dto.getResourceID()))
                .findFirst();

        if (existingMccc.isPresent()) {
            mccc = existingMccc.get();
        } else {
            mccc = new Mccc();
            mccc.setResourceId(canHaveResource.get());
        }
        return mccc;
    }

    private void handleDepartment(Mccc mccc, Authentication authentication) {
        Connection connection = connectionService.findByIdentifier(authentication.getName());

        if (connection == null) return;

        mccc.setUniversityDepartment(connection.getUniversityDepartment());
    }


    @Nullable
    private ResponseEntity<Object> fillCriticalConcepts(@NotNull McccRequest dto, @NotNull Set<CriticalConcept> setCriticalConcepts) {
        List<LearningRankModel> acs = dto.getAcsGrouped();
        for (LearningRankModel learningRank : acs) {
            String ueCode = extractCodeFromSkillTitle(learningRank.ue());
            if (ueCode == null) return ResponseEntity.badRequest().body("L'UE n'existe pas !");
            Rank correspondedRank = extractFirstRankFromRankTitle(learningRank.levels(), ueCode);
            if (correspondedRank == null) return ResponseEntity.badRequest().body("Le levels n'existe pas !");

            fillNewCriticalConcepts(setCriticalConcepts, learningRank, correspondedRank);
        }
        return null;
    }

    @Nullable
    private String extractCodeFromSkillTitle(@NotNull String skillTitle) {
        String ueCode = getFirstRegexOccurence("[0-9]+", skillTitle);
        if (ueCode == null) return null;

        if (!skillService.doSkillNumExists(Integer.parseInt(ueCode))) {
            return null;
        }
        return ueCode;
    }

    @Nullable
    private Rank extractFirstRankFromRankTitle(@NotNull String rankTitle, String ueCode) {
        String rankCode = getFirstRegexOccurence("[0-9]+", rankTitle);

        if (rankCode == null) return null;

        int rCode = Integer.parseInt(rankCode);
        int uCode = Integer.parseInt(ueCode);

        List<Rank> ranks = rankService.getRanksByNumAndUe(rCode, uCode);

        if (ranks.isEmpty()) {
            return null;
        }
        return ranks.get(0);
    }

    private void fillNewCriticalConcepts(Set<CriticalConcept> setAcs, LearningRankModel learningRank, Rank correspondedRank) {
        List<CriticalConceptModel> criticalConcepts = learningRank.acs();
        for (CriticalConceptModel criticalConcept : criticalConcepts) {
            CriticalConcept newCriticalConcept = verifyPresenceOfCriticalConcept(criticalConcept, correspondedRank);
            setAcs.add(newCriticalConcept);
        }
    }


    @NotNull
    private CriticalConcept verifyPresenceOfCriticalConcept(CriticalConceptModel criticalConcept, Rank rank) {
        List<CriticalConcept> criticalConcepts = criticalConceptService.getCriticalConceptWithNumAndTitleAndRank(criticalConcept.conceptNum(), criticalConcept.conceptTitle(), rank);
        if (criticalConcepts.isEmpty()) {
            return new CriticalConcept(criticalConcept, rank);
        } else {
            return criticalConcepts.get(0);
        }
    }

    @NotNull
    private Set<Sae> getSAEFromDto(McccRequest dto) {
        Set<Sae> setSae = new HashSet<>();
        List<SaeModel> saes = dto.getSaeCodes();
        for (SaeModel sae : saes) {
            List<Sae> correspondedSaes = saeService.getSaeByNum(sae.saeCode());
            if (!correspondedSaes.isEmpty()) {
                setSae.add(correspondedSaes.get(0));
            } else {
                Sae newSae = new Sae(sae);
                setSae.add(newSae);
            }
        }
        return setSae;
    }

    @NotNull
    private Set<Teacher> getTeachersFromDto(McccRequest dto) {
        Set<Teacher> setTeacher = new HashSet<>();
        TeacherModel[] teachers = dto.getReferents();
        for (TeacherModel teacher : teachers) {
            List<Teacher> correspondedTeachers = teacherService.findByFirstnameAndLastname(teacher.firstname(), teacher.lastname());
            if (!correspondedTeachers.isEmpty()) {
                setTeacher.add(correspondedTeachers.get(0));
            } else {
                Teacher newTeacher = new Teacher(teacher);
                setTeacher.add(newTeacher);
            }
        }
        return setTeacher;
    }

    @NotNull
    private CourseHours getCourseHoursFromDto(McccRequest dto) {
        Optional<CourseHours> allCourseHours = courseHoursService.findCourseHoursFromDatas(dto.getMinCM(), dto.getMinTD(), dto.getMinTP(), dto.getMinDSTP(), dto.getMinDS());
        if (allCourseHours.isEmpty()) {
            return new CourseHours(dto.getMinCM(), dto.getMinDS(), dto.getMinDSTP(), dto.getMinTP(), dto.getMinTD());
        }
        CourseHours courseHours = allCourseHours.get();
        courseHours.setNbMinCM(dto.getMinCM());
        courseHours.setNbMinTD(dto.getMinTD());
        courseHours.setNbMinTP(dto.getMinTP());
        courseHours.setNbMinDS(dto.getMinDS());
        courseHours.setNbMinDSTP(dto.getMinDSTP());
        return courseHours;
    }

    @GetMapping("/getTeachers")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getTeachers(Authentication authentication) {
        return ResponseEntity.ok(teacherService.getAllTeachersFromDepartment(authentication.getName()));
    }

    @GetMapping("/getSaes")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getSaes(Authentication authentication) {
        return ResponseEntity.ok(saeService.getAllSaesFromDepartment(authentication.getName()));
    }

    @GetMapping("/getHourlyVolumes")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getCourseHours() {
        return ResponseEntity.ok(courseHoursService.getAllCourseHours());
    }

    @PostMapping("/saveHourlyVolume")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> saveCourseHours(@RequestBody CourseHours courseHours) {
        courseHoursService.save(courseHours);
        return ResponseEntity.ok("Volumes horaires mis à jour avec succès !");
    }

    @GetMapping("/getHourlyVolumesID/{id}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getCourseHoursID(@PathVariable Long id) {
        Optional<CourseHours> courseHours = courseHoursService.findById(id);
        if (courseHours.isPresent()) {
            return ResponseEntity.ok(courseHours.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getCreationDate/{id}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getCreationDate(@PathVariable Long id) {
        return ResponseEntity.ok(mcccService.getCreationDateFromId(id));
    }

    @GetMapping("/getReferentIds/{id}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getReferentIds(@PathVariable Long id) {
        List<Long> teacherIds = mcccService.getTeacherIdsByMcccId(id);
        return ResponseEntity.ok(teacherIds);
    }

    @GetMapping("/available-years")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getMcccYear() {
        return ResponseEntity.ok(mcccService.getAllYears());
    }

}
