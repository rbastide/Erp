package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.ResourceSheetRequest;
import fr.iut_unilim.erp_back.dto.ResourceSheetResponse;
import fr.iut_unilim.erp_back.entity.ResourceSheet;
import fr.iut_unilim.erp_back.repository.ResourceSheetRepository;
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
    private final ResourceSheetRepository resourceSheetRepository;

    public ResourceSheetController(ResourceSheetService resourceSheetService, ResourceSheetRepository resourceSheetRepository) {
        this.resourceSheetService = resourceSheetService;
        this.resourceSheetRepository = resourceSheetRepository;
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
    public ResponseEntity<?> saveResourceSheet(@RequestBody ResourceSheetRequest resourceSheetRequest, Authentication authentication) {
        boolean hasBeenAdded = resourceSheetService.saveFromRequest(resourceSheetRequest);
        if (hasBeenAdded) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> deleteResourceSheet(@PathVariable Long id) {
        if (!resourceSheetRepository.existsById(id)) {
            return ResponseEntity.ok().build();
        }
        resourceSheetRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/getHistory")
//    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
//    public ResponseEntity<List<HistoryResponse>> getHistory(Authentication authentication) {
//        List<ResourceSheet> sheets = resourceSheetService.getAllResourceSheetsFromDepartment(authentication.getName());
//        List<HistoryResponse> historyList = new ArrayList<>();
//
//        for (ResourceSheet sheet : sheets) {
//            String code = "Inconnu";
//            String name = "Inconnue";
//
//            if (sheet.getResource() != null) {
//                Optional<Resource> res = resourceRepository.findById(sheet.getResource());
//                if (res.isPresent()) {
//                    code = res.get().getNum();
//                    name = res.get().getName();
//                }
//            }
//
//            Date dateToUse = sheet.getLastModificationDate() != null ?
//                    sheet.getLastModificationDate() : sheet.getCreationDate();
//
//            historyList.add(new HistoryResponse(
//                    sheet.getSheetID(),
//                    code,
//                    name,
//                    dateToUse));
//        }
//
//        Collections.reverse(historyList);
//
//        return ResponseEntity.ok(historyList);
//    }
}