package fr.iut_unilim.erp_back.pdf.parts;

import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import fr.iut_unilim.erp_back.entity.HourlyVolume;
import org.jetbrains.annotations.NotNull;

import static fr.iut_unilim.erp_back.pdf.PdfGenerator.decimalFormat;
import static fr.iut_unilim.erp_back.pdf.utils.CellUtils.createCenteredCell;

public class PdfHours {
    public static Table create(@NotNull String resourceCode, @NotNull HourlyVolume hourlyVolume) {
        Table hours = new Table(UnitValue.createPercentArray(new float[]{50, 10, 10, 10, 10, 10}));
        hours.useAllAvailableWidth();

        hours.addCell(createCenteredCell("Ressource ou sous ressource"));
        hours.addCell(createCenteredCell("h CM"));
        hours.addCell(createCenteredCell("h TD"));
        hours.addCell(createCenteredCell("h DS"));
        hours.addCell(createCenteredCell("h TP"));
        hours.addCell(createCenteredCell("h DS TP"));

        double[] hoursPerCourse = new double[]{hourlyVolume.getNbHoursCM(), hourlyVolume.getNbHoursTD(), hourlyVolume.getNbHoursDS(), hourlyVolume.getNbHoursTP(), hourlyVolume.getNbHoursDSTP()};

        hours.addCell(createCenteredCell(resourceCode));
        for (double hour : hoursPerCourse) {
            hours.addCell(createCenteredCell(decimalFormat.format(hour)));
        }

        return hours;
    }
}
