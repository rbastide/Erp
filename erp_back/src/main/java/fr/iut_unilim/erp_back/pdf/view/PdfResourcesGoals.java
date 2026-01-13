package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Table;
import fr.iut_unilim.erp_back.pdf.model.ResourceSheetViewModel;
import org.jetbrains.annotations.NotNull;

public class PdfResourcesGoals {
    public static Table create(@NotNull ResourceSheetViewModel resourceSheet) {
        Table table = new Table(1);
        table.useAllAvailableWidth();

        table.addCell("Objectifs de la ressources :");
        table.addCell(resourceSheet.mainGoal());

        return table;
    }
}
