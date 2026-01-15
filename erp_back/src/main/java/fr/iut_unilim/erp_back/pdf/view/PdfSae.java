package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import fr.iut_unilim.erp_back.entity.Sae;

import java.util.Set;

public class PdfSae {
    public static Table create(Set<Sae> saes) {
        Table saesTable = new Table(UnitValue.createPercentArray(new float[]{20, 90}));
        saesTable.useAllAvailableWidth();

        saesTable.addCell("SAÉ concernée(s) :");
        String mergedSaes = String.join(", ", saes.stream().map(Sae::getNum).toList());

        saesTable.addCell(mergedSaes);

        return saesTable;
    }
}
