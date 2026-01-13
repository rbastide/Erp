package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import fr.iut_unilim.erp_back.entity.Sae;

import java.util.List;

public class PdfSae {
    public static Table create(List<Sae> saes) {
        Table saesTable = new Table(UnitValue.createPercentArray(new float[]{20, 90}));
        saesTable.useAllAvailableWidth();

        saesTable.addCell("SAÉ concernée(s) :");
        StringBuilder mergedSaes = new StringBuilder();
        for (Sae sae : saes) {
            mergedSaes.append(sae.getNum());
        }
        saesTable.addCell(mergedSaes.toString());

        return saesTable;
    }
}
