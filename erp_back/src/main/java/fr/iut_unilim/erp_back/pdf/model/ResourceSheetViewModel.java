package fr.iut_unilim.erp_back.pdf.model;

import fr.iut_unilim.erp_back.entity.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

public record ResourceSheetViewModel(
        Resource resource,
        HourlyVolume hourlyVolume,
        List<PedagologicalTeachersFeedbacks> pedagologicalTeachersFeedbacks,
        List<StudentsFeedbacks> studentsFeedbacks,
        List<ImprovementIdeas> improvementIdeas,
        int semester,
        Set<Sae> saes,
        Date creationDate,
        Date lastModificationDate,
        Set<Teacher> teachers,
        Set<CriticalLearning> criticalLearnings,
        List<PedagologicalContent> pedagologicalContent,
        Set<Skill> skills) {
}
