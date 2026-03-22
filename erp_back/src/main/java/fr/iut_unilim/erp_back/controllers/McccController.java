package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.McccRequest;
import fr.iut_unilim.erp_back.entity.CourseHours;
import fr.iut_unilim.erp_back.entity.Mccc;
import fr.iut_unilim.erp_back.service.CourseHoursService;
import fr.iut_unilim.erp_back.service.McccService;
import fr.iut_unilim.erp_back.service.SaeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/mccc")
public class McccController {

    private final McccService mcccService;
    private final CourseHoursService courseHoursService;
    private final SaeService saeService;

    public McccController(McccService mcccService, CourseHoursService courseHoursService, SaeService saeService) {
        this.mcccService = mcccService;
        this.courseHoursService = courseHoursService;
        this.saeService = saeService;
    }

    @GetMapping("/mcccs/{year}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getMccc(Authentication authentication, @PathVariable Integer year) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/save")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> saveMccc(@RequestBody McccRequest dto, Authentication authentication) {
        Optional<Mccc> hasBeenSaved = mcccService.saveFromDto(dto);
        if (hasBeenSaved.isEmpty()) {
            return ResponseEntity.badRequest().body("Une erreur est survenue lors de la sauvegarde de la MCCC");
        }

        return ResponseEntity.ok("MCCC sauvegardée/mise à jour avec succès !");
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

    @PostMapping("/saveHourlyVolume")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> saveCourseHours(@RequestBody CourseHours courseHours) {
        courseHoursService.save(courseHours);
        return ResponseEntity.ok("Volumes horaires mis à jour avec succès !");
    }

    @GetMapping("/getReferentIds/{id}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getReferentIds(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/available-years")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getMcccYear() {
        return ResponseEntity.ok(mcccService.getAllYears());
    }

}
