package fr.iut_unilim.erp_back.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import fr.iut_unilim.erp_back.ErpBackApplication;
import fr.iut_unilim.erp_back.pdf.handlers.FooterHandler;
import fr.iut_unilim.erp_back.pdf.parts.PdfDescription;
import fr.iut_unilim.erp_back.pdf.view.PdfFormationInfo;
import fr.iut_unilim.erp_back.pdf.view.PdfHeader;

import java.io.IOException;

public class PdfGenerator {
    public static final String BASE_PATH = "src/main/resources/";
    private static final String IUT_ICON_PATH = "assets/logo_iut.png";
    public static final int DOCUMENT_FONT_SIZE = 10;

    public static void createPdf() {
        String path = "mon_document.pdf";

        try {
            PdfWriter writer = new PdfWriter(path);

            PdfDocument pdf = new PdfDocument(writer);

            Document document = new Document(pdf);
            document.setFontSize(DOCUMENT_FONT_SIZE);
            FooterHandler handler = new FooterHandler();
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, handler);

            Table header = PdfHeader.create(IUT_ICON_PATH);
            if (header == null) {
                return;
            }
            header.setMarginBottom(5);

            document.add(header);

            Table formationInfo = PdfFormationInfo.create();
            formationInfo.setMarginBottom(10);
            document.add(formationInfo);

            PdfDescription.addToDocument(document);

            document.close();

        } catch (IOException e) {
            ErpBackApplication.LOGGER.info("Issue with creating PDF : " + e.getMessage());
        }
    }
}
