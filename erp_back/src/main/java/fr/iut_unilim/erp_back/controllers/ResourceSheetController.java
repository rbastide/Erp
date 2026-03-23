package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.HistoryResponse;
import fr.iut_unilim.erp_back.dto.ResourceSheetRequest;
import fr.iut_unilim.erp_back.dto.ResourceSheetResponse;
import fr.iut_unilim.erp_back.entity.ResourceSheet;
import fr.iut_unilim.erp_back.service.ResourceSheetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resourceSheet")
public class ResourceSheetController {

    private final ResourceSheetService resourceSheetService;

    public ResourceSheetController(ResourceSheetService resourceSheetService) {
        this.resourceSheetService = resourceSheetService;
    }

    @GetMapping("/getResourceSheet/{year}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getResourceSheet(Authentication authentication, @PathVariable Integer year) {
        List<ResourceSheet> sheets = resourceSheetService.getAllResourceSheetsFromDepartmentAndYear(authentication.getName(), year);
        List<ResourceSheetResponse> sheetRequests = resourceSheetService.convertToEntitiesToResponses(sheets);
        return ResponseEntity.ok(sheetRequests);
    }

    @GetMapping("/resource-sheet/{resourceId}/{year}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getResourceSheetForResourceFromYear(@PathVariable Long resourceId, @PathVariable Integer year) {
        Optional<ResourceSheet> resourceSheet = resourceSheetService.getResourceSheetForResourceFromYear(resourceId, year);

        if (resourceSheet.isEmpty()) return ResponseEntity.notFound().build();

        ResourceSheetResponse resourceSheetResponse = resourceSheetService.convertEntityToResponse(resourceSheet.get());

        return ResponseEntity.ok().body(resourceSheetResponse);
    }

    @GetMapping("/resource-sheet/{resourceId}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getResourceSheetFromId(@PathVariable Long resourceId) {
        Optional<ResourceSheet> resourceSheet = resourceSheetService.getResourceSheetById(resourceId);
        if (resourceSheet.isEmpty()) return ResponseEntity.notFound().build();
        ResourceSheetResponse resourceSheetResponse = resourceSheetService.convertEntityToResponse(resourceSheet.get());
        return ResponseEntity.ok().body(resourceSheetResponse);
    }

    @PostMapping("/resource-sheet")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> saveResourceSheet(@RequestBody ResourceSheetRequest resourceSheetRequest) {
        boolean hasBeenAdded = resourceSheetService.saveFromRequest(resourceSheetRequest);
        if (!hasBeenAdded) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> deleteResourceSheet(@PathVariable Long id) {
        boolean hasBeenDeleted = resourceSheetService.deleteResourceSheetById(id);
        if (!hasBeenDeleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getHistory/{year}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<List<HistoryResponse>> getHistory(Authentication authentication, @PathVariable Integer year) {
        List<HistoryResponse> historyResponses = resourceSheetService.getHistoryResponses(authentication.getName(), year);

        return ResponseEntity.ok(historyResponses);
    }

    @GetMapping("available-years")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getResourceSheetYears() {
        return ResponseEntity.ok(resourceSheetService.getAllYears());
    }


    @GetMapping("/getNonValidateResourceSheet")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getNonValidateResourceSheet() {
        return ResponseEntity.ok(resourceSheetService.getResourceSheetNonValidate());
    }


    @PutMapping("/validate/{id}")
    public ResponseEntity<String> validateResourceSheet(@PathVariable("id") Long id) {
        boolean isUpdated = resourceSheetService.validateSheet(id);

        if (isUpdated) {
            return ResponseEntity.ok().body("La fiche ressource a bien été validée.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erreur : Fiche ressource introuvable.");
        }
    }
}