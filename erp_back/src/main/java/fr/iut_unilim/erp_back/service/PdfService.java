package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.pdf.PdfGenerator;
import fr.iut_unilim.erp_back.pdf.model.ResourceSheetViewModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PdfService {
    private final HourlyVolumeService hourlyVolumeService;
    private final ResourceService resourceService;

    public PdfService(HourlyVolumeService hourlyVolumeService, ResourceService resourceService) {
        this.hourlyVolumeService = hourlyVolumeService;
        this.resourceService = resourceService;
    }

    @Nullable
    public byte[] generateResourceSheetPdf(@NotNull ResourceSheet resourceSheet) {
        ResourceSheetViewModel resourceSheetViewModel = createResourceSheetViewModel(resourceSheet);
        if (resourceSheetViewModel == null) {
            return null;
        }

        return PdfGenerator.createPdf(resourceSheetViewModel);
    }

    @Nullable
    private ResourceSheetViewModel createResourceSheetViewModel(@NotNull ResourceSheet resourceSheet) {
        Optional<Resource> resource = resourceService.getResourceById(resourceSheet.getResourceID());
        if (resource.isEmpty()) {
            return null;
        }

        Optional<HourlyVolume> hourlyVolume = hourlyVolumeService.findById(resourceSheet.getResourceID());
        if (hourlyVolume.isEmpty()) {
            return null;
        }

        List<PedagologicalTeachersFeedbacks> pedagologicalTeachersFeedbacks = resourceSheet.getTeachersFeedbacks();

        List<StudentsFeedbacks> studentsFeedbacks = resourceSheet.getStudentsFeedbacks();

        List<ImprovementIdeas> improvementIdeas = resourceSheet.getImprovementIdeas();

        int semester = resourceSheet.getSemester();
        String mainGoal = resourceSheet.getMainGoal();

        List<Sae> saes = resourceSheet.getSaes();

        Date creationDate = resourceSheet.getCreationDate();
        Date lastModificationDate = resourceSheet.getLastModificationDate();

        List<Teacher> teachers = resourceSheet.getTeachers();

        List<Skill> skills = resourceSheet.getSkills();

        List<PedagologicalContent> pedagologicalContents = resourceSheet.getPedagologicalContent();

        return new ResourceSheetViewModel(resource.get(), hourlyVolume.get(), pedagologicalTeachersFeedbacks, studentsFeedbacks, improvementIdeas, semester, mainGoal, saes, creationDate, lastModificationDate, teachers, skills, pedagologicalContents);
    }
}
