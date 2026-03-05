package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.HistoryResponse;
import fr.iut_unilim.erp_back.dto.ResourceSheetRequest;
import fr.iut_unilim.erp_back.dto.ResourceSheetResponse;
import fr.iut_unilim.erp_back.entity.ResourceSheet;
import fr.iut_unilim.erp_back.service.ResourceSheetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resourceSheet")
@CrossOrigin(origins = "http://localhost:5173")
public class ResourceSheetController {

    private final ResourceSheetService resourceSheetService;

    public ResourceSheetController(ResourceSheetService resourceSheetService) {
        this.resourceSheetService = resourceSheetService;
    }

    @GetMapping("/getResourceSheet")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getResourceSheet(Authentication authentication) {
        List<ResourceSheet> sheets = resourceSheetService.getAllResourceSheetsFromDepartment(authentication.getName());
        List<ResourceSheetResponse> sheetRequests = resourceSheetService.convertToEntitiesToResponses(sheets);
        return ResponseEntity.ok(sheetRequests);
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

    @GetMapping("/getHistory")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<List<HistoryResponse>> getHistory(Authentication authentication) {
        List<HistoryResponse> historyResponses = resourceSheetService.getHistoryResponses(authentication.getName());

        return ResponseEntity.ok(historyResponses);
    }
}