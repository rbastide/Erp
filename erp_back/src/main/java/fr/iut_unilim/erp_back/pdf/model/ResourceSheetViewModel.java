package fr.iut_unilim.erp_back.pdf.model;

import fr.iut_unilim.erp_back.entity.*;

import java.util.Date;
import java.util.List;

public record ResourceSheetViewModel(
        Resource resource,
        HourlyVolume hourlyVolume,
        List<PedagologicalTeachersFeedbacks> pedagologicalTeachersFeedbacks,
        List<StudentsFeedbacks> studentsFeedbacks,
        List<ImprovementIdeas> improvementIdeas,
        int semester,
        String mainGoal,
        List<Sae> saes,
        Date creationDate,
        Date lastModificationDate,
        List<Teacher> teachers,
        List<Skill> skills,
        List<PedagologicalContent> pedagologicalContent
) {
}
