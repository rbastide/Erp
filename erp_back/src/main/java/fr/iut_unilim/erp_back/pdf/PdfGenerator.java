package fr.iut_unilim.erp_back.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import fr.iut_unilim.erp_back.ErpBackApplication;
import fr.iut_unilim.erp_back.pdf.handlers.FooterHandler;
import fr.iut_unilim.erp_back.pdf.parts.PdfDescription;
import fr.iut_unilim.erp_back.pdf.parts.PdfHours;
import fr.iut_unilim.erp_back.pdf.view.PdfFeedbacks;
import fr.iut_unilim.erp_back.pdf.view.PdfFormationInfo;
import fr.iut_unilim.erp_back.pdf.view.PdfHeader;
import fr.iut_unilim.erp_back.pdf.view.PdfPedalogicalContent;

import java.io.ByteArrayOutputStream;

import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createTitle;

public class PdfGenerator {
    public static final String BASE_PATH = "erp_back/src/main/resources/";
    private static final String IUT_ICON_PATH = "assets/logo_iut.png";
    public static final int DOCUMENT_FONT_SIZE = 10;

    public static byte[] createPdf() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);
        document.setFontSize(DOCUMENT_FONT_SIZE);
        FooterHandler handler = new FooterHandler();
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, handler);

        ErpBackApplication.LOGGER.info("Created pdf");
        if (!generateFirstPage(document)) return null;

        document.add(new AreaBreak());

        ErpBackApplication.LOGGER.info("Created pdf");
        if (!generateSecondPage(document)) return null;

        document.add(new AreaBreak());

        ErpBackApplication.LOGGER.info("Created pdf");
        if (!generateThirdPage(document)) return null;

        ErpBackApplication.LOGGER.info("Created pdf");
        document.close();

        return baos.toByteArray();
    }

    private static boolean generateFirstPage(Document document) {
        Table header = PdfHeader.create(IUT_ICON_PATH);
        if (header == null) {
            return false;
        }
        header.setMarginBottom(13);

        document.add(header);

        Table formationInfo = PdfFormationInfo.create();
        formationInfo.setMarginBottom(10);
        document.add(formationInfo);

        PdfDescription.addToDocument(document);
        return true;
    }

    private static boolean generateSecondPage(Document document) {
        Table header = PdfHeader.create(IUT_ICON_PATH);
        if (header == null) {
            return false;
        }
        header.setMarginBottom(5);

        document.add(header);

        document.add(createTitle("Heures :"));
        document.add(PdfHours.create());

        document.add(createTitle("Contenu pédagogique :"));
        document.add(PdfPedalogicalContent.create());

        return true;
    }

    private static boolean generateThirdPage(Document document) {
        Table header = PdfHeader.create(IUT_ICON_PATH);
        if (header == null) {
            return false;
        }
        header.setMarginBottom(5);

        document.add(header);

        document.add(createTitle("Suivi de la ressource / module :"));
        document.add(PdfFeedbacks.create());

        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        infoTable.useAllAvailableWidth();
        infoTable.setMarginTop(50);

        infoTable.setBorder(Border.NO_BORDER);

        Cell dateCell = new Cell().add(new Paragraph("Date : 17 juillet 2025"))
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER);
        infoTable.addCell(dateCell);

        Cell refCell = new Cell().add(new Paragraph("Référent module : Thomas Hügel"))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER);
        infoTable.addCell(refCell);

        document.add(infoTable);

        return true;
    }
}
