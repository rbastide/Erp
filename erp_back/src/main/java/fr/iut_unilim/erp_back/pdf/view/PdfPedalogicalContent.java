package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import fr.iut_unilim.erp_back.entity.EducationalContent;

import java.util.List;

import static fr.iut_unilim.erp_back.pdf.utils.HtmlToPdfTextUtils.toPdfText;

public class PdfPedalogicalContent {
    public static Table create(List<EducationalContent> educationalContents) {
        Table pedalogicalContent = new Table(1);
        pedalogicalContent.useAllAvailableWidth();

        for (EducationalContent educationalContent : educationalContents) {
            String contentType = educationalContent.getClassType().getClassTypeName() + " " + educationalContent.getCourseNumber();
            Paragraph paragraph = new Paragraph(contentType + " : " + toPdfText(educationalContent.getContent()));
            pedalogicalContent.addCell(paragraph);
        }

        return pedalogicalContent;
    }
}
