package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import static fr.iut_unilim.erp_back.pdf.utils.HtmlToPdfTextUtils.toPdfText;

public class PdfFeedbacks {
    public static Table create(String teachersFeedbacks, String studentsFeedbacks, String improvementIdeas) {
        Table feedbacks = new Table(UnitValue.createPercentArray(new float[]{20, 80}));
        feedbacks.useAllAvailableWidth();

        feedbacks.addCell(new Cell(1, 1).add(new Paragraph("Retour de l'équipe pédagogique")));
        feedbacks.addCell(new Cell().add(new Paragraph(toPdfText(teachersFeedbacks))));

        feedbacks.addCell(new Cell(1, 1).add(new Paragraph("Retour des étudiants")));
        feedbacks.addCell(new Cell().add(new Paragraph(toPdfText(studentsFeedbacks))));

        feedbacks.addCell(new Cell(1, 1).add(new Paragraph("Idée d'améliorations")));
        feedbacks.addCell(new Cell().add(new Paragraph(toPdfText(improvementIdeas))));

        return feedbacks;
    }
}
