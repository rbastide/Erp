package fr.iut_unilim.erp_back.pdf.model;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.jetbrains.annotations.Nullable;

import static fr.iut_unilim.erp_back.pdf.PdfGenerator.BASE_PATH;
import static fr.iut_unilim.erp_back.pdf.utils.ImageDataUtils.createImageData;
import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createCenteredParagraph;
import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createMultipleLinesParagraph;

public class PdfHeader {
    private static final int HEADER_MULTIPLE_PARAGRAPH_SIZE = 7;
    private static final int HEADER_MULTIPLE_PARAGRAPH_FIXED_LEADING = 5;

    @Nullable
    public static Table create(String iconPath) {
        String imagePath = BASE_PATH + iconPath;

        ImageData dataLogoIut = createImageData(imagePath);
        if (dataLogoIut == null) return null;

        Image logoIut = new Image(dataLogoIut);
        logoIut.setAutoScale(true);

        Cell cellIconIut = new Cell();
        cellIconIut.add(logoIut);

        String[] informationParagraphsLines = new String[]{"Référence : \t UI en FOR 001",
                "Date de création : \t 13/04/2021",
                "Date de modification : \t 17/07/2025"};

        Table table = new Table(UnitValue.createPercentArray(new float[]{30, 20, 20, 30}));
        table.useAllAvailableWidth();
        table.setAutoLayout();
        table.addCell(cellIconIut);
        table.addCell(createStyledCell("Département : Informatique"));
        table.addCell(createStyledCell("Fiche ressource"));
        table.addCell(createMultipleLinesParagraph(informationParagraphsLines, HEADER_MULTIPLE_PARAGRAPH_SIZE, HEADER_MULTIPLE_PARAGRAPH_FIXED_LEADING));

        return table;
    }

    private static Cell createStyledCell(String text) {
        return new Cell()
                .add(createCenteredParagraph(text))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setPadding(5);
    }
}
