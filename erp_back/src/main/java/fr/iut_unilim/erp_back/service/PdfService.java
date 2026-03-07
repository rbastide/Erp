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
    private final CourseHoursService courseHoursService;
    private final ResourceService resourceService;
    private final McccService mcccService;

    public PdfService(CourseHoursService courseHoursService, ResourceService resourceService, McccService mcccService) {
        this.courseHoursService = courseHoursService;
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
        Optional<Resource> canHaveResource = resourceService.getResourceById(resourceSheet.getResource().getResourceID());
        if (canHaveResource.isEmpty()) {
            return null;
        }
        Resource resource = canHaveResource.get();

        Optional<CourseHours> courseHours = courseHoursService.findById(resourceSheet.getResource().getResourceID());
        if (courseHours.isEmpty()) {
            return null;
        }

        int semester = resource.getSemester();

        Optional<Mccc> canHaveMccc = mcccService.getCurrentMcccFromResource(resource);
        if (canHaveMccc.isEmpty()) {
            return null;
        }
        Mccc mccc = canHaveMccc.get();

        McccDatas fromMccc = handleFromMccc(resourceSheet, mccc, resource);

        if (fromMccc.skills() == null) {
            return null;
        }

        List<EducationalContent> educationallContents = resourceSheet.getEducationalContents();

        return new ResourceSheetViewModel(resource, courseHours.get(),
                resourceSheet.getTeacherFeedbacks().getContent(), resourceSheet.getStudentFeedbacks().getContent(),
                resourceSheet.getImprovementIdeas().getContent(), semester,
                fromMccc.saes(), fromMccc.creationDate(),
                fromMccc.lastModificationDate(), fromMccc.teachers(),
                fromMccc.criticalConcepts(), educationallContents, fromMccc.skills());
    }

    @NotNull
    private McccDatas handleFromMccc(@NotNull ResourceSheet resourceSheet, Mccc mccc, Resource resource) {
        Set<Sae> saes = mccc.getSaesId();

        Date creationDate = resourceSheet.getCreationDate();
        Date lastModificationDate = resourceSheet.getLastModificationDate();

        Set<Teacher> teachers = mccc.getReferencialTeacherId();

        Set<CriticalConcept> criticalConcepts = mccc.getCriticalConceptsId();
        Set<Skill> skills = mcccService.getSkillsByResource(resource);
        return new McccDatas(saes, creationDate, lastModificationDate, teachers, criticalConcepts, skills);
    }

    private record McccDatas(Set<Sae> saes, Date creationDate, Date lastModificationDate, Set<Teacher> teachers,
                             Set<CriticalConcept> criticalConcepts, Set<Skill> skills) {
    }
}
