package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.entity.ResourceSheet;
import fr.iut_unilim.erp_back.service.PdfService;
import fr.iut_unilim.erp_back.service.ResourceSheetService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public ResponseEntity<?> getResourceSheet(@PathVariable Long id) {
        Optional<ResourceSheet> canHaveResourceSheet = resourceSheetService.getResourceSheetFromId(id);
        if (canHaveResourceSheet.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] pdfContent = pdfService.generateResourceSheetPdf(canHaveResourceSheet.get());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("fiche_ressource_" + id + ".pdf")
                .build());

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}
