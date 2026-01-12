package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.service.PdfService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pdf")
public class PdfController {
    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/resource-sheet/{id}")
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public ResponseEntity<?> getResourceSheet(@PathVariable Integer id) {
        byte[] pdfContent = pdfService.generateResourceSheetPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("fiche_ressource_" + id + ".pdf")
                .build());

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}
