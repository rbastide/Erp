package fr.iut_unilim.erp_back;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import fr.iut_unilim.erp_back.pdf.PdfHeader;

import java.io.IOException;

public class PdfCreatorApplication {
    public static final String BASE_PATH = "src/main/resources/";
    private static final String IUT_ICON_PATH = "assets/logo_iut.png";

    public static void main(String[] args) {
        generatePdf();
    }

    public static void generatePdf() {
        String path = "mon_document.pdf";

        try {
            PdfWriter writer = new PdfWriter(path);

            PdfDocument pdf = new PdfDocument(writer);

            Document document = new Document(pdf);

            Table header = PdfHeader.createInfoCard(IUT_ICON_PATH);
            if (header == null) {
                return;
            }
            document.add(header);

            document.add(new Paragraph("Hello World!"));

            List list = new List()
                    .add("Élément 1")
                    .add("Élément 2")
                    .add("Élément 3");
            document.add(list);

            document.close();

        } catch (IOException e) {
            ErpBackApplication.LOGGER.info("Issue with creating PDF : " + e.getMessage());
        }
    }
}
