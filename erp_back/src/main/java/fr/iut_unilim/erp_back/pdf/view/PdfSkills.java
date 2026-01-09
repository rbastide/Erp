package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import fr.iut_unilim.erp_back.pdf.utils.CellUtils;

import java.util.*;

public class PdfSkills {
    private static final float BORDER_WIDTH = 1.f;

    private static final Map<String, List<String>> SKILLS_LABELS = new HashMap<>() {{
        put("Concevoir et mettre en place une base de données à partir d'un cahier des charges client",
                new ArrayList<>(Arrays.asList(
                        "Mettre à jour et interroger une base de données relationnelle (en requêtes directes ou à travers une application)",
                        "Visualiser des données",
                        "Concevoir une base de données relationnelle à partir d'un cahier des charges"
                ))
        );
        put("Concevoir et mettre en place une liaison front et back",
                new ArrayList<>(Arrays.asList(
                        "Mettre à jour et interroger une base de données relationnelle (en requêtes directes ou à travers une application)",
                        "Visualiser des données",
                        "Concevoir une base de données relationnelle à partir d'un cahier des charges"
                )));
    }};

    public static Table create() {
        Table table = new Table(UnitValue.createPercentArray(new float[]{25, 25, 25, 25}));
        table.useAllAvailableWidth();
        table.setBorder(Border.NO_BORDER);

        table.addCell(CellUtils.createCenteredCell("Compétences", 1, 2).setBorder(new SolidBorder(BORDER_WIDTH)));
        table.addCell(CellUtils.createCenteredCell("Apprentissages critiques", 1, 2).setBorder(new SolidBorder(BORDER_WIDTH)));

        for (Map.Entry<String, List<String>> skill : SKILLS_LABELS.entrySet()) {
            List<String> learnings = skill.getValue();
            int nbLearnings = learnings.size();

            Cell cellTitle = new Cell(nbLearnings, 2)
                    .setBorder(Border.NO_BORDER)
                    .setBorderLeft(new SolidBorder(BORDER_WIDTH))
                    .setBorderRight(new SolidBorder(BORDER_WIDTH))
                    .setBorderBottom(new SolidBorder(BORDER_WIDTH))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.CENTER);

            cellTitle.add(new Paragraph(skill.getKey()));
            table.addCell(cellTitle);

            for (int i = 0; i < nbLearnings; i++) {
                Cell cellLearning = new Cell(1, 2)
                        .setBorder(Border.NO_BORDER)
                        .setBorderRight(new SolidBorder(BORDER_WIDTH))
                        .setBorderBottom(new SolidBorder(BORDER_WIDTH));

                cellLearning.add(new Paragraph(learnings.get(i)));

                table.addCell(cellLearning);
            }
        }

        return table;
    }
}
