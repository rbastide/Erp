package fr.iut_unilim.erp_back.pdf.parts;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import fr.iut_unilim.erp_back.pdf.model.ResourceSheetViewModel;
import fr.iut_unilim.erp_back.pdf.view.PdfSae;
import fr.iut_unilim.erp_back.pdf.view.PdfSkills;

import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createSubTitle;

public class PdfDescription {
    public static void addToDocument(Document document, ResourceSheetViewModel resourceSheet) {
        document.add(createSubTitle("Compétences :"));
        document.add(PdfSkills.create(resourceSheet.criticalConcepts()));

        Table saes = PdfSae.create(resourceSheet.saes());
        saes.setMarginTop(4);
        document.add(saes);
    }
}
