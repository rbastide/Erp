package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.ErpBackApplication;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.pdf.PdfGenerator;
import fr.iut_unilim.erp_back.pdf.model.ResourceSheetViewModel;
import org.jetbrains.annotations.NotNull;
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

    public byte[] generateResourceSheetPdf(@NotNull ResourceSheet resourceSheet, @NotNull String requestId) {
        ResourceSheetViewModel resourceSheetViewModel = createResourceSheetViewModel(resourceSheet, requestId);
        return PdfGenerator.createPdf(resourceSheetViewModel, requestId);
    }

    private ResourceSheetViewModel createResourceSheetViewModel(@NotNull ResourceSheet resourceSheet, @NotNull String requestId) {
        Optional<Resource> canHaveResource = resourceService.getResourceById(resourceSheet.getResource().getResourceID());
        if (canHaveResource.isEmpty()) {
            throw new IllegalStateException("[" + requestId + "] Resource introuvable pour la fiche " + resourceSheet.getSheetID());
        }
        Resource resource = canHaveResource.get();

        Optional<CourseHours> courseHours = courseHoursService.findById(resourceSheet.getResource().getResourceID());
        if (courseHours.isEmpty()) {
            throw new IllegalStateException("[" + requestId + "] Volumes horaires introuvables pour la ressource " + resource.getResourceID());
        }

        int semester = resource.getSemester();

        Optional<Mccc> canHaveMccc = mcccService.getCurrentMcccFromResource(resource);
        if (canHaveMccc.isEmpty()) {
            throw new IllegalStateException("[" + requestId + "] MCCC introuvable pour la ressource " + resource.getNum());
        }
        Mccc mccc = canHaveMccc.get();

        McccDatas fromMccc = handleFromMccc(resourceSheet, mccc, resource);

        if (fromMccc.skills() == null) {
            throw new IllegalStateException("[" + requestId + "] Compétences introuvables pour la ressource " + resource.getNum());
        }

        List<EducationalContent> educationallContents = resourceSheet.getEducationalContents();
        if (educationallContents == null) {
            throw new IllegalStateException("[" + requestId + "] Contenu pédagogique introuvable pour la fiche " + resourceSheet.getSheetID());
        }

        if (resourceSheet.getTeacherFeedbacks() == null || resourceSheet.getStudentFeedbacks() == null || resourceSheet.getImprovementIdeas() == null) {
            throw new IllegalStateException("[" + requestId + "] Feedbacks incomplets pour la fiche " + resourceSheet.getSheetID());
        }

        ErpBackApplication.LOGGER.info("PDF [" + requestId + "] données prêtes pour la fiche " + resourceSheet.getSheetID()
                + " (resource=" + resource.getNum() + ", contenus=" + educationallContents.size() + ")");

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
