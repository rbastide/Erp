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
import java.util.HashSet;
import java.util.ArrayList;

@Service
public class PdfService {
    private final ResourceService resourceService;
    private final McccService mcccService;

    public PdfService(ResourceService resourceService, McccService mcccService) {
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

        CourseHours courseHours = resourceSheet.getCourseHours();

        int semester = resource.getSemester();

        Optional<Mccc> canHaveMccc = mcccService.getCurrentMcccFromResource(resource, resourceSheet.getAcademicYearStart());
        Mccc mccc = null;
        if (canHaveMccc.isEmpty()) {
            ErpBackApplication.LOGGER.warning("[" + requestId + "] MCCC introuvable pour la ressource " + resource.getNum() + ". Le PDF sera généré avec des valeurs par défaut.");
        } else {
            mccc = canHaveMccc.get();
        }

        McccDatas fromMccc = handleFromMccc(resourceSheet, mccc, resource);

        List<EducationalContent> educationalContents = resourceSheet.getEducationalContents();
        if (educationalContents == null) {
            educationalContents = new ArrayList<>();
        }

        String teacherFeedback = resourceSheet.getTeacherFeedbacks() != null ? resourceSheet.getTeacherFeedbacks().getContent() : "";
        String studentFeedback = resourceSheet.getStudentFeedbacks() != null ? resourceSheet.getStudentFeedbacks().getContent() : "";
        String improvementIdeas = resourceSheet.getImprovementIdeas() != null ? resourceSheet.getImprovementIdeas().getContent() : "";

        Date creationDate = resourceSheet.getCreationDate() != null ? resourceSheet.getCreationDate() : new Date();
        Date lastModificationDate = resourceSheet.getLastModificationDate() != null ? resourceSheet.getLastModificationDate() : new Date();

        ErpBackApplication.LOGGER.info("PDF [" + requestId + "] données prêtes pour la fiche " + resourceSheet.getSheetID()
                + " (resource=" + resource.getNum() + ", contenus=" + educationalContents.size() + ")");

        return new ResourceSheetViewModel(resource, courseHours,
                teacherFeedback, studentFeedback, improvementIdeas, semester,
                fromMccc.saes(), creationDate,
                lastModificationDate, fromMccc.teachers(),
                fromMccc.criticalConcepts(), educationalContents, fromMccc.skills());
    }

    @NotNull
    private McccDatas handleFromMccc(@NotNull ResourceSheet resourceSheet, Mccc mccc, Resource resource) {
        Set<Sae> saes = (mccc != null && mccc.getSaesId() != null) ? mccc.getSaesId() : new HashSet<>();
        Set<TeacherResource> teachers = (mccc != null && mccc.getTeacherResources() != null) ? mccc.getTeacherResources() : new HashSet<>();
        Set<CriticalConcept> criticalConcepts = (mccc != null && mccc.getCriticalConceptsId() != null) ? mccc.getCriticalConceptsId() : new HashSet<>();

        Date creationDate = resourceSheet.getCreationDate() != null ? resourceSheet.getCreationDate() : new Date();
        Date lastModificationDate = resourceSheet.getLastModificationDate() != null ? resourceSheet.getLastModificationDate() : new Date();

        Set<Skill> skills = mcccService.getSkillsByResource(resource, resourceSheet.getAcademicYearStart());
        if (skills == null) {
            skills = new HashSet<>();
        }

        return new McccDatas(saes, creationDate, lastModificationDate, teachers, criticalConcepts, skills);
    }

    private record McccDatas(Set<Sae> saes, Date creationDate, Date lastModificationDate, Set<TeacherResource> teachers,
                             Set<CriticalConcept> criticalConcepts, Set<Skill> skills) {
    }
}