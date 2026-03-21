package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.McccRequest;
import fr.iut_unilim.erp_back.dto.McccResponse;
import fr.iut_unilim.erp_back.entity.CourseHours;
import fr.iut_unilim.erp_back.service.*;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static fr.iut_unilim.erp_back.tools.utils.RegexManipulation.getFirstRegexOccurence;

@RestController
@RequestMapping("/api/mccc")
public class McccController {

    private final McccService mcccService;
    private final CourseHoursService courseHoursService;
    private final ResourceService resourceService;
    private final SaeService saeService;
    private final SkillService skillService;
    private final RankService rankService;
    private final CriticalConceptService criticalConceptService;
    private final ConnectionService connectionService;

    public McccController(McccService mcccService, CourseHoursService courseHoursService, ResourceService resourceService, SaeService saeService, SkillService skillService, RankService rankService, CriticalConceptService criticalConceptService, ConnectionService connectionService) {
        this.mcccService = mcccService;
        this.courseHoursService = courseHoursService;
        this.resourceService = resourceService;
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
        return ResponseEntity.ok("MCCC sauvegardée/mise à jour avec succès !");
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

    @GetMapping("/getTeachers")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getTeachers(Authentication authentication) {
        // TODO : recreate this method with new teachers services
        return ResponseEntity.ok(null);
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
