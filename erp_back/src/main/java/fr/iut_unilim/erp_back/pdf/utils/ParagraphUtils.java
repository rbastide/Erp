package fr.iut_unilim.erp_back.pdf.utils;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

public class ParagraphUtils {
    public static Paragraph createCenteredParagraph(String text) {
        Paragraph headerParagraph = new Paragraph(text);
        headerParagraph.setTextAlignment(TextAlignment.CENTER);
        headerParagraph.setVerticalAlignment(VerticalAlignment.MIDDLE);
        return headerParagraph;
    }

    public static Paragraph createMultipleLinesParagraph(String[] args, int fontSize, int fixedLeading) {
        Paragraph paragraph = new Paragraph();
        for (String arg : args) {
            paragraph.add(createCenteredParagraph(arg));
        }
        paragraph.setFontSize(fontSize);
        paragraph.setFixedLeading(fixedLeading);
        return paragraph;
    }
}
