package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import fr.iut_unilim.erp_back.pdf.utils.CellUtils;

import java.util.Arrays;

import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createCenteredParagraph;

public class PdfFormationInfo {
    private static final int[] HOURS = new int[]{4, 6, 30};
    private static final String[] HOURS_CLASS_CORRESPONDANCE = new String[]{"CM", "TD", "TP"};

    public static Table create() {
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{33, 33, 11, 11, 11}));
        infoTable.useAllAvailableWidth();

        infoTable.addCell(new Cell(1, 5).add(createCenteredParagraph("Semestre 2")));

        infoTable.addCell(CellUtils.createCenteredCell("UE 2.4"));

        infoTable.addCell(CellUtils.createCenteredCell("Exploitation d'une base de données"));

        for (int i = 0; i < HOURS.length; i++) {
            infoTable.addCell(CellUtils.createCenteredCell(HOURS[i] + "h " + HOURS_CLASS_CORRESPONDANCE[i]));
        }

        Cell cellTeachers = new Cell();
        cellTeachers.add(new Paragraph("Référents : Je sais pas"));
        infoTable.addCell(cellTeachers);

        infoTable.addCell(CellUtils.createCenteredCell("R2.4"));

        int totalHours = Arrays.stream(HOURS).sum();
        infoTable.addCell(CellUtils.createCenteredCell(totalHours + "h / étudiant", 1, 3));

        return infoTable;
    }
}
