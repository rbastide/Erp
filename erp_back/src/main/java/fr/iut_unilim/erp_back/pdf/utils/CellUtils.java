package fr.iut_unilim.erp_back.pdf.utils;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createCenteredParagraph;

public class CellUtils {
    public static Cell createCenteredCell(String text) {
        return new Cell()
                .add(createCenteredParagraph(text))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setPadding(5);
    }

    public static Cell createCenteredCell(String text, int rowSpan, int columnSpan) {
        return new Cell(rowSpan, columnSpan)
                .add(createCenteredParagraph(text))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setPadding(5);
    }
}
