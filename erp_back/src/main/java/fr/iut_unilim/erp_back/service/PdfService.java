package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.ResourceSheet;
import fr.iut_unilim.erp_back.pdf.PdfGenerator;
import org.springframework.stereotype.Service;

@Service
public class PdfService {
    public byte[] generateResourceSheetPdf(ResourceSheet resourceSheet) {
        return PdfGenerator.createPdf(resourceSheet);
    }
}
