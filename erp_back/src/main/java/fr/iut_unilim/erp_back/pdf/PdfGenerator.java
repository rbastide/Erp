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
import fr.iut_unilim.erp_back.entity.Teacher;
import fr.iut_unilim.erp_back.pdf.handlers.FooterHandler;
import fr.iut_unilim.erp_back.pdf.model.ResourceSheetViewModel;
import fr.iut_unilim.erp_back.pdf.parts.PdfDescription;
import fr.iut_unilim.erp_back.pdf.parts.PdfHours;
import fr.iut_unilim.erp_back.pdf.view.PdfFeedbacks;
import fr.iut_unilim.erp_back.pdf.view.PdfFormationInfo;
import fr.iut_unilim.erp_back.pdf.view.PdfHeader;
import fr.iut_unilim.erp_back.pdf.view.PdfPedalogicalContent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createTitle;

public class PdfGenerator {
    public static DecimalFormat decimalFormat = new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.FRANCE));

    public static final String BASE_PATH = "erp_back/src/main/resources/";
    private static final String IUT_ICON_PATH = "assets/logo_iut.png";
    public static final int DOCUMENT_FONT_SIZE = 10;

    @Nullable
    public static byte[] createPdf(ResourceSheetViewModel resourceSheet) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);
        document.setFontSize(DOCUMENT_FONT_SIZE);
        FooterHandler handler = new FooterHandler(resourceSheet.resource().getNum(), new ArrayList<>());
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, handler);

        List<String> referancialTeachers = resourceSheet.teachers().stream().map(PdfGenerator::mergeFirstNameAndLastName).toList();
        String referencialTeachersString = String.join(", ", referancialTeachers);

        ErpBackApplication.LOGGER.info("Generating PDF for resource " + resourceSheet.resource().getNum());
        if (!generateFirstPage(document, resourceSheet, referencialTeachersString)) return null;

        document.add(new AreaBreak());
        ErpBackApplication.LOGGER.info("Generating PDF for resource " + resourceSheet.resource().getNum());
        if (!generateSecondPage(document, resourceSheet)) return null;

        document.add(new AreaBreak());
        ErpBackApplication.LOGGER.info("Generating PDF for resource " + resourceSheet.resource().getNum());
        if (!generateThirdPage(document, resourceSheet, referencialTeachersString)) return null;
        ErpBackApplication.LOGGER.info("Generating PDF for resource " + resourceSheet.resource().getNum());
        document.close();

        return baos.toByteArray();
    }

    private static boolean generateFirstPage(Document document, ResourceSheetViewModel resourceSheet, String referencialTeachersString) {
        Table header = PdfHeader.create(IUT_ICON_PATH, resourceSheet);
        if (header == null) {
            return false;
        }
        header.setMarginBottom(13);

        document.add(header);

        Table formationInfo = PdfFormationInfo.create(resourceSheet.resource(), resourceSheet.hourlyVolume(), referencialTeachersString, resourceSheet.skills());
        formationInfo.setMarginBottom(10);
        document.add(formationInfo);

        PdfDescription.addToDocument(document, resourceSheet);
        return true;
    }

    private static boolean generateSecondPage(Document document, ResourceSheetViewModel resourceSheet) {
        Table header = PdfHeader.create(IUT_ICON_PATH, resourceSheet);
        if (header == null) {
            return false;
        }
        header.setMarginBottom(5);

        document.add(header);

        document.add(createTitle("Heures :"));
        document.add(PdfHours.create(resourceSheet.resource().getNum(), resourceSheet.hourlyVolume()));

        document.add(createTitle("Contenu pédagogique :"));
        document.add(PdfPedalogicalContent.create(resourceSheet.pedagologicalContent()));

        return true;
    }

    private static boolean generateThirdPage(Document document, ResourceSheetViewModel resourceSheet, String referencialTeachersString) {
        Table header = PdfHeader.create(IUT_ICON_PATH, resourceSheet);
        if (header == null) {
            return false;
        }
        header.setMarginBottom(5);

        document.add(header);

        document.add(createTitle("Suivi de la ressource / module :"));
        document.add(PdfFeedbacks.create(resourceSheet.pedagologicalTeachersFeedbacks(), resourceSheet.studentsFeedbacks(), resourceSheet.improvementIdeas()));

        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        infoTable.useAllAvailableWidth();
        infoTable.setMarginTop(50);

        infoTable.setBorder(Border.NO_BORDER);

        Cell dateCell = new Cell().add(new Paragraph("Date : " + resourceSheet.creationDate()))
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER);
        infoTable.addCell(dateCell);

        Cell refCell = new Cell().add(new Paragraph("Référent module : " + referencialTeachersString))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER);
        infoTable.addCell(refCell);

        document.add(infoTable);

        return true;
    }

    @NotNull
    private static String mergeFirstNameAndLastName(@NotNull Teacher teacher) {
        return teacher.getFirstname() + " " + teacher.getLastname();
    }
}
