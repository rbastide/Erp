package fr.iut_unilim.erp_back;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.IOException;

public class PdfCreatorApplication {
    public static void main(String[] args) {
        generatePdf();
    }

    public static void generatePdf() {
        String path = "mon_document.pdf";

        try {
            PdfWriter writer = new PdfWriter(path);

            PdfDocument pdf = new PdfDocument(writer);

            Document document = new Document(pdf);

            document.add(new Paragraph("Hello World!"));

            List list = new List()
                    .add("Élément 1")
                    .add("Élément 2")
                    .add("Élément 3");
            document.add(list);

            float[] columnWidths = {100, 200};
            Table table = new Table(columnWidths);
            table.addCell("ID");
            table.addCell("Nom");
            document.add(table);

            document.close();

            System.out.println("PDF créé avec succès !");

        } catch (IOException e) {
            ErpBackApplication.LOGGER.info("Issue with creating PDF");
        }
    }
}
