package fr.iut_unilim.erp_back.pdf.parts;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import fr.iut_unilim.erp_back.pdf.view.PdfResourcesGoals;
import fr.iut_unilim.erp_back.pdf.view.PdfSae;
import fr.iut_unilim.erp_back.pdf.view.PdfSkills;

import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createSubTitle;
import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createTitle;

public class PdfDescription {
    public static void addToDocument(Document document) {
        document.add(createTitle("Descriptif de la ressource :"));
        document.add(PdfResourcesGoals.create());

        document.add(createSubTitle("Compétences :"));
        document.add(PdfSkills.create());

        Table saes = PdfSae.create();
        saes.setMarginTop(4);
        document.add(saes);
    }
}
