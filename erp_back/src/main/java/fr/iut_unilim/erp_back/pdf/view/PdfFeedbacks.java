package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

public class PdfFeedbacks {
    private static final String[] teamFeedbacks = {
            "Les images utilisées sont à revoir",
            "Certaines erreurs sont à corriger",
            "Il faut s'assurer que chaque intervenant ait la même attente globale de la ressource"
    };

    private static final String[] studentsFeedbacks = {
            "Plus d'exemples dans les CM",
            "Des exercices qui couvrent mieux"
    };

    public static Table create() {
        Table feedbacks = new Table(UnitValue.createPercentArray(new float[]{20, 80}));
        feedbacks.useAllAvailableWidth();

        feedbacks.addCell(new Cell(teamFeedbacks.length, 1).add(new Paragraph("Retour de l'équipe pédagogique")));
        for (String teamFeedback : teamFeedbacks) {
            feedbacks.addCell(teamFeedback);
        }

        feedbacks.addCell(new Cell(studentsFeedbacks.length, 1).add(new Paragraph("Retour des étudiants")));
        for (String studentFeedback : studentsFeedbacks) {
            feedbacks.addCell(studentFeedback);
        }

        return feedbacks;
    }
}
