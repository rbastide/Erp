package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import fr.iut_unilim.erp_back.entity.CriticalConcept;
import fr.iut_unilim.erp_back.pdf.utils.CellUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PdfSkills {
    private static final float BORDER_WIDTH = 1.f;

    public static Table create(@NotNull Set<CriticalConcept> criticalConcepts) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{25, 25, 25, 25}));
        table.useAllAvailableWidth();
        table.setBorder(Border.NO_BORDER);

        table.addCell(CellUtils.createCenteredCell("Compétences", 1, 2).setBorder(new SolidBorder(BORDER_WIDTH)));
        table.addCell(CellUtils.createCenteredCell("Apprentissages critiques", 1, 2).setBorder(new SolidBorder(BORDER_WIDTH)));

        addSkillsToTable(table, criticalConcepts);

        return table;
    }

    private static void addSkillsToTable(Table table, Set<CriticalConcept> criticalConcepts) {
        Map<String, List<String>> skills = flattenSkills(criticalConcepts);
        for (Map.Entry<String, List<String>> skill : skills.entrySet()) {
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
    }

    private static Map<String, List<String>> flattenSkills(@NotNull Set<CriticalConcept> criticalConcepts) {
        Map<String, List<String>> flattennedSkills = new HashMap<>();

        for (CriticalConcept criticalConcept : criticalConcepts) {
            String skillName = criticalConcept.getRankID().getSkillID().getSkillName();
            List<String> criticalConceptsFromSkills = flattennedSkills.getOrDefault(skillName, new ArrayList<>());
            criticalConceptsFromSkills.add(criticalConcept.getCriticalConceptTitle());
            flattennedSkills.put(skillName, criticalConceptsFromSkills);
        }

        return flattennedSkills;
    }
}
