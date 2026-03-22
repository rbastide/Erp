package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.McccRequest;
import fr.iut_unilim.erp_back.dto.McccResponse;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Mccc;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.service.ConnectionService;
import fr.iut_unilim.erp_back.service.McccService;
import fr.iut_unilim.erp_back.service.SaeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mccc")
public class McccController {

    private final McccService mcccService;
    private final SaeService saeService;
    private final ConnectionService connectionService;

    public McccController(McccService mcccService, SaeService saeService, ConnectionService connectionService) {
        this.mcccService = mcccService;
        this.saeService = saeService;
        this.connectionService = connectionService;
    }

    @GetMapping("/mcccs/{year}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getMccc(Authentication authentication, @PathVariable Integer year) {
        UniversityDepartment universityDepartment = connectionService.findByDepartmentFromAuthentification(authentication);
        List<McccResponse> mcccResponses = mcccService.getMcccsByDepartmentAndYear(universityDepartment, year);
        return ResponseEntity.ok(mcccResponses);
    }

    @PostMapping("/save")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> saveMccc(@RequestBody McccRequest dto, Authentication authentication) {
        Connection connection = connectionService.findByIdentifier(authentication.getName());
        Optional<Mccc> hasBeenSaved = mcccService.saveFromDto(dto, connection);
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
