package fr.iut_unilim.erp_back.pdf.handlers;

import com.itextpdf.commons.actions.IEventHandler;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEvent;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEventHandler;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.TextAlignment;

public class FooterHandler extends AbstractPdfDocumentEventHandler implements IEventHandler {
    private static final int WIDTH_MARGIN = 60;

    @Override
    protected void onAcceptedEvent(AbstractPdfDocumentEvent event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfPage page = docEvent.getPage();
        int pageNumber = docEvent.getDocument().getPageNumber(page);

        Rectangle pageSize = page.getPageSize();
        float textY = 20;
        float lineY = 40;

        PdfCanvas pdfCanvas = new PdfCanvas(page);
        pdfCanvas.moveTo(pageSize.getLeft() + WIDTH_MARGIN, lineY);
        pdfCanvas.lineTo(pageSize.getRight() - WIDTH_MARGIN, lineY);
        pdfCanvas.stroke();
        Canvas canvas = new Canvas(pdfCanvas, pageSize);

        canvas.setBorderTop(new SolidBorder(1));
        canvas.showTextAligned("Page " + pageNumber, page.getPageSize().getWidth() - WIDTH_MARGIN, textY, TextAlignment.RIGHT);
        canvas.showTextAligned("UE 2.4" + "/ R2.06", WIDTH_MARGIN, textY, TextAlignment.LEFT);

        canvas.close();
    }
}
