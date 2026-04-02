package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.ErpBackApplication;
import fr.iut_unilim.erp_back.dto.McccRequest;
import fr.iut_unilim.erp_back.dto.ResourceResponse;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Mccc;
import fr.iut_unilim.erp_back.entity.McccResponse;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.service.ConnectionService;
import fr.iut_unilim.erp_back.service.McccImportService;
import fr.iut_unilim.erp_back.service.McccService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mccc")
public class McccController {

    private final McccService mcccService;
    private final ConnectionService connectionService;
    private final McccImportService mcccImportService;

    public McccController(McccService mcccService, ConnectionService connectionService, McccImportService mcccImportService) {
        this.mcccService = mcccService;
        this.connectionService = connectionService;
        this.mcccImportService = mcccImportService;
    }

    @GetMapping("/mcccs/{year}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getMcccResources(Authentication authentication, @PathVariable Integer year) {
        UniversityDepartment universityDepartment = connectionService.findByDepartmentFromAuthentification(authentication);
        List<ResourceResponse> mcccResponses = mcccService.getMcccsByDepartmentAndYear(universityDepartment, year);
        return ResponseEntity.ok(mcccResponses);
    }

    @PostMapping("/save")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> saveMccc(@RequestBody McccRequest dto, Authentication authentication) {
        Optional<Connection> connectionOptional = connectionService.findByIdentifier(authentication.getName());
        if (connectionOptional.isEmpty()) return ResponseEntity.badRequest().build();

        Connection connection = connectionOptional.get();
        Optional<Mccc> hasBeenSaved = mcccService.saveFromDto(dto, connection);
        if (hasBeenSaved.isEmpty()) {
            return ResponseEntity.badRequest().body("Une erreur est survenue lors de la sauvegarde de la MCCC");
        }

        return ResponseEntity.ok("MCCC sauvegardée/mise à jour avec succès !");
    }

    @GetMapping("/{year}/{resourceID}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getMccc(@PathVariable Integer year, @PathVariable Long resourceID) {
        Optional<Mccc> mcccOptional = mcccService.getCurrentMcccFromResource(resourceID, year);
        if (mcccOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Mccc mccc = mcccOptional.get();
        McccResponse mcccResponse = mcccService.convertToEntityToResponse(mccc);
        return ResponseEntity.ok(mcccResponse);
    }

    @GetMapping("/available-years")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getMcccYear() {
        return ResponseEntity.ok(mcccService.getAllYears());
    }

    @GetMapping("/mcccs/all")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<List<Mccc>> getAllMcccs() {
        return ResponseEntity.ok(mcccService.getAllMcccs());
    }

    @PostMapping("/import")
    public ResponseEntity<?> importMcccFromExcel(@RequestParam("file") MultipartFile file, @RequestParam("year") Integer year, Authentication authentication) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Le fichier est vide.");
        }

        Optional<Connection> connection = connectionService.findByIdentifier(authentication.getName());
        if (connection.isEmpty()) return ResponseEntity.badRequest().build();

        try {
            mcccImportService.importExcelFile(file, year, connection.get());
            return ResponseEntity.ok("Les MCCC ont été importées et sauvegardées avec succès !");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de l'importation : " + e.getMessage());
        }
    }


}
