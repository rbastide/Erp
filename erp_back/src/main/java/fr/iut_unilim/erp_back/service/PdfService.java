package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.pdf.PdfGenerator;
import org.springframework.stereotype.Service;

@Service
public class PdfService {
    public byte[] generateResourceSheetPdf() {
        return PdfGenerator.createPdf();
    }
}
