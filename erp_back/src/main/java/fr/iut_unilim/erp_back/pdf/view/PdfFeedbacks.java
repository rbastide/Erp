package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import fr.iut_unilim.erp_back.entity.ImprovementIdeas;
import fr.iut_unilim.erp_back.entity.PedagologicalTeachersFeedbacks;
import fr.iut_unilim.erp_back.entity.StudentsFeedbacks;

import java.util.List;

public class PdfFeedbacks {
    public static Table create(List<PedagologicalTeachersFeedbacks> teachersFeedbacks, List<StudentsFeedbacks> studentsFeedbacks, List<ImprovementIdeas> improvementIdeas) {
        Table feedbacks = new Table(UnitValue.createPercentArray(new float[]{20, 80}));
        feedbacks.useAllAvailableWidth();

        feedbacks.addCell(new Cell(teachersFeedbacks.size(), 1).add(new Paragraph("Retour de l'équipe pédagogique")));
        for (PedagologicalTeachersFeedbacks teacherFeedback : teachersFeedbacks) {
            feedbacks.addCell(teacherFeedback.content());
        }

        feedbacks.addCell(new Cell(studentsFeedbacks.size(), 1).add(new Paragraph("Retour des étudiants")));
        for (StudentsFeedbacks studentFeedback : studentsFeedbacks) {
            feedbacks.addCell(studentFeedback.content());
        }

        feedbacks.addCell(new Cell(improvementIdeas.size(), 1).add(new Paragraph("Idée d'améliorations")));
        for (ImprovementIdeas improvementIdea : improvementIdeas) {
            feedbacks.addCell(improvementIdea.idea());
        }

        return feedbacks;
    }
}
