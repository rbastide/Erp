package fr.iut_unilim.erp_back.controllers;


import fr.iut_unilim.erp_back.dto.McccResponse;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.service.*;
import fr.iut_unilim.erp_back.tools.datastructures.LearningRank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private final CriticalLearningService criticalLearningService;

    public McccController(McccService mcccService, HourlyVolumeService hourlyVolumeService, ResourceService resourceService, TeacherService teacherService, SaeService saeService, SkillService skillService, RankService rankService, CriticalLearningService criticalLearningService) {
        this.mcccService = mcccService;
        this.hourlyVolumeService = hourlyVolumeService;
        this.resourceService = resourceService;
        this.teacherService = teacherService;
        this.saeService = saeService;
        this.skillService = skillService;
        this.rankService = rankService;
        this.criticalLearningService = criticalLearningService;
    }

    @Nullable
    private static ResponseEntity<Object> getCreationdateAndEditDateFromDto(McccResponse dto, Mccc mccc) {
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

    @PostMapping("/save")
    public ResponseEntity<?> saveMccc(@RequestBody McccResponse dto) {
        Mccc mccc = new Mccc();

        List<Resource> resources = resourceService.getFromNum(dto.getResourceCode());
        if (!resources.isEmpty()) {
            Resource resource = resources.get(0);
            mccc.setResourceId(resource);
        } else {
            return ResponseEntity.badRequest().body("Le code de ressource n'existe pas !");
        }

        Set<Teacher> setTeacher = getTeachersFromDto(dto);
        mccc.setReferencialTeacherId(setTeacher);

        Set<Sae> setSae = getSAEFromDto(dto);
        mccc.setSaesId(setSae);

        HourlyVolume hourlyVolume = getHourlyVolumeFromDto(dto);
        mccc.setHourlyVolId(hourlyVolume);

        ResponseEntity<Object> doDatesHasCrashed = getCreationdateAndEditDateFromDto(dto, mccc);
        if (doDatesHasCrashed != null) return doDatesHasCrashed;

        Set<CriticalLearning> setCriticalLearnings = new HashSet<>();
        ResponseEntity<Object> doCriticalLearningHasCrashed = fillCriticalLearnings(dto, setCriticalLearnings);
        if (doCriticalLearningHasCrashed != null) return doCriticalLearningHasCrashed;

        mccc.setCriticalLearningsId(setCriticalLearnings);

        mcccService.save(mccc);

        return ResponseEntity.ok("MCCC sauvegardée avec succès !");
    }

    @Nullable
    private ResponseEntity<Object> fillCriticalLearnings(@NotNull McccResponse dto, @NotNull Set<CriticalLearning> setCriticalLearnings) {
        List<fr.iut_unilim.erp_back.tools.datastructures.LearningRank> acs = dto.getAcsGrouped();
        for (fr.iut_unilim.erp_back.tools.datastructures.LearningRank learningRank : acs) {
            String ueCode = extractCodeFromSkillTitle(learningRank.ue());
            if (ueCode == null) return ResponseEntity.badRequest().body("L'UE n'existe pas !");

            Rank correspondedRank = extractFirstRankFromRankTitle(learningRank.niveau());
            if (correspondedRank == null) return ResponseEntity.badRequest().body("Le niveau n'existe pas !");

            fillNewCriticalLearnings(setCriticalLearnings, learningRank, correspondedRank);
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

    private void fillNewCriticalLearnings(Set<CriticalLearning> setAcs, LearningRank learningRank, Rank correspondedRank) {
        List<fr.iut_unilim.erp_back.tools.datastructures.CriticalLearning> criticalLearnings = learningRank.acs();
        for (fr.iut_unilim.erp_back.tools.datastructures.CriticalLearning criticalLearning : criticalLearnings) {
            CriticalLearning newCriticalLearning = verifyPresenceOfCriticalLearning(criticalLearning, correspondedRank);
            setAcs.add(newCriticalLearning);
        }
    }


    @NotNull
    private CriticalLearning verifyPresenceOfCriticalLearning(fr.iut_unilim.erp_back.tools.datastructures.CriticalLearning criticalLearning, Rank rank) {
        List<CriticalLearning> criticalLearnings = criticalLearningService.getCriticalLearningsWithNumAndTitleAndRank(criticalLearning.learningNum(), criticalLearning.learningTitle(), rank);
        if (criticalLearnings.isEmpty()) {
            return new CriticalLearning(criticalLearning, rank);
        } else {
            return criticalLearnings.get(0);
        }
    }

    @NotNull
    private Set<Sae> getSAEFromDto(McccResponse dto) {
        Set<Sae> setSae = new HashSet<>();
        List<fr.iut_unilim.erp_back.tools.datastructures.SAE> saes = dto.getSaeCodes();
        for (fr.iut_unilim.erp_back.tools.datastructures.SAE sae : saes) {
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
    private Set<Teacher> getTeachersFromDto(McccResponse dto) {
        Set<Teacher> setTeacher = new HashSet<>();
        fr.iut_unilim.erp_back.tools.datastructures.Teacher[] teachers = dto.getReferents();
        for (fr.iut_unilim.erp_back.tools.datastructures.Teacher teacher : teachers) {
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
    private HourlyVolume getHourlyVolumeFromDto(McccResponse dto) {
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
}