package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.util.List;

public class PdfSae {
    private static final List<String> SAEs = List.of("S2.04", "S2.05");

    public static Table create() {
        Table saes = new Table(UnitValue.createPercentArray(new float[]{20, 90}));
        saes.useAllAvailableWidth();

        saes.addCell("SAÉ concernée(s) :");
        String mergedSaes = String.join(", ", SAEs);
        saes.addCell(mergedSaes);

        return saes;
    }
}
