package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import fr.iut_unilim.erp_back.pdf.model.ResourceSheetViewModel;
import fr.iut_unilim.erp_back.pdf.utils.CellUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static fr.iut_unilim.erp_back.pdf.utils.ImageDataUtils.createImageData;
import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createMultipleLinesParagraph;

public class PdfHeader {
    private static final int HEADER_MULTIPLE_PARAGRAPH_SIZE = 7;
    private static final int HEADER_MULTIPLE_PARAGRAPH_FIXED_LEADING = 5;

    @Nullable
    public static Table create(String iconPath, @NotNull ResourceSheetViewModel resourceSheet) {
        ImageData dataLogoIut = createImageData(iconPath);
        if (dataLogoIut == null) return null;

        Image logoIut = new Image(dataLogoIut);
        logoIut.setAutoScale(true);

        Cell cellIconIut = new Cell();
        cellIconIut.add(logoIut);

        String[] informationParagraphsLines = new String[]{"Référence : \t IU en FOR 001",
                "Date de création : \t " + resourceSheet.creationDate(),
                "Date de modification : \t " + resourceSheet.lastModificationDate()};

        Table table = new Table(UnitValue.createPercentArray(new float[]{30, 20, 20, 30}));
        table.useAllAvailableWidth();
        table.setAutoLayout();
        table.addCell(cellIconIut);
        table.addCell(CellUtils.createCenteredCell("Département : Informatique"));
        table.addCell(CellUtils.createCenteredCell("Fiche ressource"));
        table.addCell(createMultipleLinesParagraph(informationParagraphsLines, HEADER_MULTIPLE_PARAGRAPH_SIZE, HEADER_MULTIPLE_PARAGRAPH_FIXED_LEADING));

        return table;
    }
}
