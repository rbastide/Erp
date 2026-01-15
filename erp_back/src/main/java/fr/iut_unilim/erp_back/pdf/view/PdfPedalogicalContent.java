package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import fr.iut_unilim.erp_back.entity.PedagologicalContent;

import java.util.List;

public class PdfPedalogicalContent {
    public static Table create(List<PedagologicalContent> pedagologicalContents) {
        Table pedalogicalContent = new Table(1);
        pedalogicalContent.useAllAvailableWidth();

        for (PedagologicalContent pedagologicalContent : pedagologicalContents) {
            String contentType = pedagologicalContent.getClassTypeId().getClassType() + " " + pedagologicalContent.getCourseNumber();
            Paragraph paragraph = new Paragraph(contentType + " : " + pedagologicalContent.getContent());
            pedalogicalContent.addCell(paragraph);
        }

        return pedalogicalContent;
    }
}
