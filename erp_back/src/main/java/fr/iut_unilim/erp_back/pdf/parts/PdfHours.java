package fr.iut_unilim.erp_back.pdf.parts;

import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static fr.iut_unilim.erp_back.pdf.utils.CellUtils.createCenteredCell;

public class PdfHours {
    private static final DecimalFormat df = new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.FRANCE));

    private static final Map<String, List<Double>> resources = Map.of(
            "R2.06a", List.of(1.5, 6., 1., 18., 2.),
            "R2.06b", List.of(1.5, 6., 1., 18., 2.)
    );

    public static Table create() {
        Table hours = new Table(UnitValue.createPercentArray(new float[]{50, 10, 10, 10, 10, 10}));
        hours.useAllAvailableWidth();

        hours.addCell(createCenteredCell("Ressource ou sous ressource"));
        hours.addCell(createCenteredCell("h CM"));
        hours.addCell(createCenteredCell("h TD"));
        hours.addCell(createCenteredCell("h DS"));
        hours.addCell(createCenteredCell("h TP"));
        hours.addCell(createCenteredCell("h DS TP"));

        double[] totals = new double[5];

        for (Map.Entry<String, List<Double>> hoursData : resources.entrySet()) {
            hours.addCell(createCenteredCell(hoursData.getKey()));

            List<Double> values = hoursData.getValue();
            for (int i = 0; i < values.size(); i++) {
                Double val = values.get(i);
                hours.addCell(createCenteredCell(df.format(val)));

                totals[i] += val;
            }
        }

        hours.addCell(createCenteredCell("Total par étudiant :"));
        for (double colTotal : totals) {
            hours.addCell(createCenteredCell(df.format(colTotal)));
        }

        return hours;
    }
}
