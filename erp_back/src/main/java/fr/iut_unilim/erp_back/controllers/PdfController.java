package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.ErpBackApplication;
import fr.iut_unilim.erp_back.entity.ResourceSheet;
import fr.iut_unilim.erp_back.service.PdfService;
import fr.iut_unilim.erp_back.service.ResourceSheetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/pdf")
public class PdfController {
    private final ResourceSheetService resourceSheetService;
    private final PdfService pdfService;

    public PdfController(ResourceSheetService resourceSheetService, PdfService pdfService) {
        this.resourceSheetService = resourceSheetService;
        this.pdfService = pdfService;
    }

    @GetMapping("/resource-sheet/{id}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getResourceSheet(@PathVariable Long id, Authentication authentication, HttpServletRequest request) {
        String requestId = request.getHeader("X-Debug-Request-Id");
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString().substring(0, 8);
        }
        String username = authentication != null ? authentication.getName() : "anonymous";
        ErpBackApplication.LOGGER.info("PDF [" + requestId + "] requête reçue pour la fiche " + id + ", user=" + username);

        Optional<ResourceSheet> canHaveResourceSheet = resourceSheetService.getResourceSheetById(id);
        if (canHaveResourceSheet.isEmpty()) {
            ErpBackApplication.LOGGER.warning("PDF [" + requestId + "] fiche introuvable: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] pdfContent;
        try {
            pdfContent = pdfService.generateResourceSheetPdf(canHaveResourceSheet.get(), requestId);
        } catch (RuntimeException e) {
            ErpBackApplication.LOGGER.severe("PDF [" + requestId + "] erreur de génération pour la fiche " + id + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Impossible de générer le PDF (requestId=" + requestId + "): " + e.getMessage());
        }
        if (pdfContent.length == 0) {
            ErpBackApplication.LOGGER.severe("PDF [" + requestId + "] génération vide pour la fiche " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("PDF vide généré (requestId=" + requestId + ").");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdfContent.length);
        headers.add("X-Debug-Request-Id", requestId);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("fiche_ressource_" + id + ".pdf")
                .build());

        ErpBackApplication.LOGGER.info("PDF [" + requestId + "] réponse envoyée pour la fiche " + id + " (" + pdfContent.length + " octets)");
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}
