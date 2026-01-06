package fr.iut_unilim.erp_back.controllers;


import fr.iut_unilim.erp_back.dto.McccResponse;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.service.*;
import fr.iut_unilim.erp_back.tools.datastructures.SAE;
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

@RestController
@RequestMapping("/api/mccc")
@CrossOrigin(origins = "http://localhost:5173")
public class McccController {

    private final McccService mcccService;
    private final HourlyVolumeService hourlyVolumeService;
    private final ResourceService resourceService;
    private final TeacherService teacherService;
    private final SaeService saeService;

    public McccController(McccService mcccService, HourlyVolumeService hourlyVolumeService, ResourceService resourceService, TeacherService teacherService, SaeService saeService) {
        this.mcccService = mcccService;
        this.hourlyVolumeService = hourlyVolumeService;
        this.resourceService = resourceService;
        this.teacherService = teacherService;
        this.saeService = saeService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMccc(@RequestBody McccResponse dto) {
        Mccc mccc = new Mccc();


        List<Resource> resources = resourceService.getFromName(dto.getResourceCode());
        if (!resources.isEmpty()) {
            Resource resource = resources.get(0);
            mccc.setResourceId(resource);
        }

        Set<Teacher> setTeacher = getTeachersFromDto(dto);
        mccc.setReferencialTeacherId(setTeacher);

        Set<Sae> setSae = getSAEFromDto(dto);
        mccc.setSaeId(setSae);

        HourlyVolume hourlyVolume = getHourlyVolumeFromDto(dto);
        mccc.setHourlyVolId(hourlyVolume);

        ResponseEntity<Object> build = getCreationdateAndEditdateFromDto(dto, mccc);
        if (build != null) return build;



        mcccService.saveMccc(mccc);

        return ResponseEntity.ok("MCCC sauvegardée avec succès !");
    }

    @Nullable
    private static ResponseEntity<Object> getCreationdateAndEditdateFromDto(McccResponse dto, Mccc mccc) {
        String creationDate = dto.getCreationDate();
        String editDate = dto.getEditDate();
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(creationDate);
            Date editableDate = new SimpleDateFormat("dd/MM/yyyy").parse(editDate);
            mccc.setCreationDate(date);
            mccc.setLastModificationDate(editableDate);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        return null;
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
                saeService.save(newSae);
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
                teacherService.save(newTeacher);
                setTeacher.add(newTeacher);
            }
        }
        return setTeacher;
    }

    @NotNull
    private HourlyVolume getHourlyVolumeFromDto(McccResponse dto) {
        List<HourlyVolume> hourlyVolumes = hourlyVolumeService.getAllHourlyVolumesFromDatas(dto.getHoursCM(), dto.getHoursTD(), dto.getHoursTP(), dto.getHoursDSTP());
        if (hourlyVolumes.isEmpty()) {
            HourlyVolume hourlyVolume = new HourlyVolume(dto.getHoursCM(), dto.getHoursDS(), dto.getHoursDSTP(), dto.getHoursTP(), dto.getHoursTD());
            hourlyVolumeService.save(hourlyVolume);
            return hourlyVolume;
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