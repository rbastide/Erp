package fr.iut_unilim.erp_back.pdf.model;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.util.Arrays;

public class PdfFormationInfo {
    private static final int[] HOURS = new int[]{4, 6, 30};
    private static final String[] HOURS_CLASS_CORRESPONDANCE = new String[]{"CM", "TD", "TP"};

    public static Table create() {
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{33, 33, 11, 11, 11}));
        infoTable.useAllAvailableWidth();

        Cell cellSkill = new Cell();
        cellSkill.add(new Paragraph("UE 2.4"));
        infoTable.addCell(cellSkill);

        Cell cellResource = new Cell();
        cellResource.add(new Paragraph("Exploitation d'une base de données"));
        infoTable.addCell(cellResource);

        for (int i = 0; i < HOURS.length; i++) {
            Cell cellHour = new Cell();
            cellHour.add(new Paragraph(HOURS[i] + "h " + HOURS_CLASS_CORRESPONDANCE[i]));
            infoTable.addCell(cellHour);
        }

        Cell cellTeachers = new Cell();
        cellTeachers.add(new Paragraph("Référents : Je sais pas"));
        infoTable.addCell(cellTeachers);

        Cell cellResourceCode = new Cell();
        cellResourceCode.add(new Paragraph("R2.4"));
        infoTable.addCell(cellResourceCode);

        int totalHours = Arrays.stream(HOURS).sum();
        Cell cellTotalHours = new Cell(1, 3);
        Paragraph paragraphTotalHours = new Paragraph(totalHours + "h / étudiant");
        paragraphTotalHours.setTextAlignment(TextAlignment.CENTER);
        paragraphTotalHours.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cellTotalHours.add(paragraphTotalHours);
        cellTotalHours.setTextAlignment(TextAlignment.CENTER);
        cellTotalHours.setVerticalAlignment(VerticalAlignment.MIDDLE);
        infoTable.addCell(cellTotalHours);

        return infoTable;
    }
}
