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
import java.util.Set;

@Service
public class PdfService {
    private final HourlyVolumeService hourlyVolumeService;
    private final ResourceService resourceService;
    private final McccService mcccService;

    public PdfService(HourlyVolumeService hourlyVolumeService, ResourceService resourceService, McccService mcccService) {
        this.hourlyVolumeService = hourlyVolumeService;
        this.resourceService = resourceService;
        this.mcccService = mcccService;
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
        Optional<Resource> canHaveResource = resourceService.getResourceById(resourceSheet.getResourceID());
        if (canHaveResource.isEmpty()) {
            return null;
        }
        Resource resource = canHaveResource.get();

        Optional<HourlyVolume> hourlyVolume = hourlyVolumeService.findById(resourceSheet.getResourceID());
        if (hourlyVolume.isEmpty()) {
            return null;
        }

        List<PedagologicalTeachersFeedbacks> pedagologicalTeachersFeedbacks = resourceSheet.getTeachersFeedbacks();

        List<StudentsFeedbacks> studentsFeedbacks = resourceSheet.getStudentsFeedbacks();

        List<ImprovementIdeas> improvementIdeas = resourceSheet.getImprovementIdeas();

        int semester = resource.getSemester();

        Mccc mccc = mcccService.getCurrentMcccFromResource(resource);

        if (mccc == null) {
            return null;
        }

        Set<Sae> saes = mccc.getSaesId();

        Date creationDate = resourceSheet.getCreationDate();
        Date lastModificationDate = resourceSheet.getLastModificationDate();

        Set<Teacher> teachers = mccc.getReferencialTeacherId();

        Set<CriticalLearning> criticalLearnings = mccc.getCriticalLearningsId();
        Set<Skill> skills = mcccService.getSkillsByResource(resource);

        if (skills == null) {
            return null;
        }

        List<PedagologicalContent> pedagologicalContents = resourceSheet.getPedagologicalContentId();

        return new ResourceSheetViewModel(resource, hourlyVolume.get(), pedagologicalTeachersFeedbacks, studentsFeedbacks, improvementIdeas, semester, saes, creationDate, lastModificationDate, teachers, criticalLearnings, pedagologicalContents, skills);
    }
}
