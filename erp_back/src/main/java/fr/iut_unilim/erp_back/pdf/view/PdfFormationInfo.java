package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import fr.iut_unilim.erp_back.entity.HourlyVolume;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.entity.Skill;
import fr.iut_unilim.erp_back.pdf.utils.CellUtils;
import fr.iut_unilim.erp_back.service.McccService;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static fr.iut_unilim.erp_back.pdf.PdfGenerator.decimalFormat;
import static fr.iut_unilim.erp_back.pdf.utils.ParagraphUtils.createCenteredParagraph;

public class PdfFormationInfo {
    private static final String[] HOURS_CLASS_CORRESPONDANCE = new String[]{"CM", "TD", "TP"};

    public static Table create(@NotNull Resource resource, @NotNull HourlyVolume hourlyVolume, @NotNull String referencialTeachersString, McccService mcccService) {
        Table infoTable = new Table(UnitValue.createPercentArray(new float[]{33, 33, 11, 11, 11}));
        infoTable.useAllAvailableWidth();

        infoTable.addCell(new Cell(1, 5).add(createCenteredParagraph("Semestre " + resource.getSemester())));

        Set<Skill> skills = mcccService.getSkillsByResource(resource);
        if (skills == null) {
            return null;
        }

        List<String> skillsNames = skills.stream().map(Skill::getSkillName).toList();

        infoTable.addCell(CellUtils.createCenteredCell(String.join(", ", skillsNames)));

        infoTable.addCell(CellUtils.createCenteredCell(resource.getName()));

        double[] hours = new double[]{hourlyVolume.getNbHoursCM(), hourlyVolume.getNbHoursTD(), hourlyVolume.getNbHoursTP()};
        for (int i = 0; i < hours.length; i++) {
            infoTable.addCell(CellUtils.createCenteredCell(hours[i] + "h " + HOURS_CLASS_CORRESPONDANCE[i]));
        }

        Cell cellTeachers = new Cell();
        cellTeachers.add(new Paragraph("Référents : " + referencialTeachersString));
        infoTable.addCell(cellTeachers);

        infoTable.addCell(CellUtils.createCenteredCell(resource.getNum()));

        double totalHours = Arrays.stream(hours).sum();
        infoTable.addCell(CellUtils.createCenteredCell(decimalFormat.format(totalHours) + "h / étudiant", 1, 3));

        return infoTable;
    }
}
