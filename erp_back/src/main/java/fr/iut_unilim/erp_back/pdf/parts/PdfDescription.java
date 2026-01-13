package fr.iut_unilim.erp_back.pdf.parts;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import fr.iut_unilim.erp_back.entity.CriticalLearning;
import fr.iut_unilim.erp_back.pdf.model.ResourceSheetViewModel;
import fr.iut_unilim.erp_back.pdf.view.PdfResourcesGoals;
import fr.iut_unilim.erp_back.pdf.view.PdfSae;
import fr.iut_unilim.erp_back.pdf.view.PdfSkills;
import fr.iut_unilim.erp_back.service.McccService;

import java.util.Set;

import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createSubTitle;
import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createTitle;

public class PdfDescription {
    public static void addToDocument(Document document, ResourceSheetViewModel resourceSheet, McccService mcccService) {
        document.add(createTitle("Descriptif de la ressource :"));
        document.add(PdfResourcesGoals.create(resourceSheet));

        Set<CriticalLearning> criticalLearning = mcccService.getCriticalLearningsByResource(resourceSheet.resource());

        if (criticalLearning == null) {
            return;
        }

        document.add(createSubTitle("Compétences :"));
        document.add(PdfSkills.create(criticalLearning));

        Table saes = PdfSae.create(resourceSheet.saes());
        saes.setMarginTop(4);
        document.add(saes);
    }
}
