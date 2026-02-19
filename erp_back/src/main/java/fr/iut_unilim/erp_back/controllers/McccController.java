package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.McccRequest;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.model.CriticalConceptModel;
import fr.iut_unilim.erp_back.model.LearningRankModel;
import fr.iut_unilim.erp_back.model.SaeModel;
import fr.iut_unilim.erp_back.model.TeacherModel;
import fr.iut_unilim.erp_back.service.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static fr.iut_unilim.erp_back.tools.utils.RegexManipulation.getFirstRegexOccurence;

@RestController
@RequestMapping("/api/mccc")
@CrossOrigin(origins = "http://localhost:5173")
public class McccController {

    private final McccService mcccService;
    private final HourlyVolumeService hourlyVolumeService;
    private final ResourceService resourceService;
    private final TeacherService teacherService;
    private final SaeService saeService;
    private final SkillService skillService;
    private final RankService rankService;
    private final CriticalConceptService criticalConceptService;
    private final ConnectionService connectionService;

    public McccController(McccService mcccService, HourlyVolumeService hourlyVolumeService, ResourceService resourceService, TeacherService teacherService, SaeService saeService, SkillService skillService, RankService rankService, CriticalConceptService criticalConceptService, ConnectionService connectionService) {
        this.mcccService = mcccService;
        this.hourlyVolumeService = hourlyVolumeService;
        this.resourceService = resourceService;
        this.teacherService = teacherService;
        this.saeService = saeService;
        this.skillService = skillService;
        this.rankService = rankService;
        this.criticalConceptService = criticalConceptService;
        this.connectionService = connectionService;
    }

    @Nullable
    private static ResponseEntity<Object> getCreationdateAndEditDateFromDto(McccRequest dto, Mccc mccc) {
        String creationDate = dto.getCreationDate();
        String editDate = dto.getEditDate();
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(creationDate);
            Date editableDate = new SimpleDateFormat("dd/MM/yyyy").parse(editDate);
            mccc.setCreationDate(date);
            mccc.setLastModificationDate(editableDate);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Format de date non valide ! (dd/MM/yyyy)");
        }
        return null;
    }

    @GetMapping("/mcccs")
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public ResponseEntity<?> getMccc(Authentication authentication) {
        return ResponseEntity.ok(mcccService.getAllMcccFromDepartment(authentication.getName()));
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveMccc(@RequestBody McccRequest dto, Authentication authentication) {
        Optional<Resource> canHaveResource = resourceService.getResourceById(dto.getResourceID());
        if (canHaveResource.isEmpty()) {
            return ResponseEntity.badRequest().body("Le code de ressource n'existe pas !");
        }

        Mccc mccc = getCurrentMccc(dto, canHaveResource);

        handleDepartment(mccc, authentication);

        Set<Teacher> setTeacher = getTeachersFromDto(dto);
        mccc.setReferencialTeacherId(setTeacher);

        Set<Sae> setSae = getSAEFromDto(dto);
        mccc.setSaesId(setSae);

        HourlyVolume hourlyVolume = getHourlyVolumeFromDto(dto);
        mccc.setHourlyVolId(hourlyVolume);

        try {
            if (dto.getEditDate() != null) {
                Date editableDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dto.getEditDate());
                mccc.setLastModificationDate(editableDate);
            } else {
                mccc.setLastModificationDate(new Date());
            }
            if (mccc.getCreationDate() == null) {
                if (dto.getCreationDate() != null) {
                    Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dto.getCreationDate());
                    mccc.setCreationDate(date);
                } else {
                    mccc.setCreationDate(new Date());
                }
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Format de date non valide ! (dd/MM/yyyy)");
        }

        Set<CriticalConcept> setCriticalConcepts = new HashSet<>();
        ResponseEntity<Object> doCriticalConceptsHasCrashed = fillCriticalConcepts(dto, setCriticalConcepts);
        if (doCriticalConceptsHasCrashed != null) return doCriticalConceptsHasCrashed;

        mccc.setCriticalConceptsId(setCriticalConcepts);

        mcccService.save(mccc);

        return ResponseEntity.ok("MCCC sauvegardée/mise à jour avec succès !");
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

            Rank correspondedRank = extractFirstRankFromRankTitle(learningRank.levels());
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
    private Rank extractFirstRankFromRankTitle(@NotNull String rankTitle) {
        String rankCode = getFirstRegexOccurence("[0-9]+", rankTitle);
        if (rankCode == null) return null;

        if (!rankService.doRankNumExists(Integer.parseInt(rankCode))) {
            return null;
        }

        return rankService.getRanksByNum(Integer.parseInt(rankCode)).get(0);
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
    private HourlyVolume getHourlyVolumeFromDto(McccRequest dto) {
        List<HourlyVolume> hourlyVolumes = hourlyVolumeService.getAllHourlyVolumesFromDatas(dto.getHoursCM(), dto.getHoursTD(), dto.getHoursTP(), dto.getHoursDSTP());
        if (hourlyVolumes.isEmpty()) {
            return new HourlyVolume(dto.getHoursCM(), dto.getHoursDS(), dto.getHoursDSTP(), dto.getHoursTP(), dto.getHoursTD());
        }
        HourlyVolume hourlyVolume = hourlyVolumes.get(0);
        hourlyVolume.setNbHoursCM(dto.getHoursCM());
        hourlyVolume.setNbHoursTD(dto.getHoursTD());
        hourlyVolume.setNbHoursTP(dto.getHoursTP());
        hourlyVolume.setNbHoursDS(dto.getHoursDS());
        hourlyVolume.setNbHoursDSTP(dto.getHoursDSTP());
        return hourlyVolume;
    }

    @GetMapping("/getTeachers")
    public ResponseEntity<?> getTeachers(Authentication authentication) {
        return ResponseEntity.ok(teacherService.getAllTeachersFromDepartment(authentication.getName()));
    }

    @GetMapping("/getSaes")
    public ResponseEntity<?> getSaes(Authentication authentication) {
        return ResponseEntity.ok(saeService.getAllSaesFromDepartment(authentication.getName()));
    }

    @GetMapping("/getHourlyVolumes")
    public ResponseEntity<?> getHourlyVolumes() {
        return ResponseEntity.ok(hourlyVolumeService.getAllHourlyVolume());
    }

    @PostMapping("/saveHourlyVolume")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveHourlyVolume(@RequestBody HourlyVolume hourlyVolume) {
        hourlyVolumeService.save(hourlyVolume);
        return ResponseEntity.ok("Volumes horaires mis à jour avec succès !");
    }

    @GetMapping("/getHourlyVolumesID/{id}")
    public ResponseEntity<?> getHourlyVolumesID(@PathVariable Long id) {
        Optional<HourlyVolume> hourlyVolume = hourlyVolumeService.findById(id);
        if (hourlyVolume.isPresent()) {
            return ResponseEntity.ok(hourlyVolume.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getCreationDate/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getCreationDate(@PathVariable Long id) {
        return ResponseEntity.ok(mcccService.getCreationDateFromId(id));
    }

    @GetMapping("/getReferentIds/{id}")
    public ResponseEntity<?> getReferentIds(@PathVariable Long id) {
        List<Long> teacherIds = mcccService.getTeacherIdsByMcccId(id);
        return ResponseEntity.ok(teacherIds);
    }
}