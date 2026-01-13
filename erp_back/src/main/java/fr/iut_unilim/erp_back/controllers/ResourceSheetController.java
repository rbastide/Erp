package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.ErpBackApplication;
import fr.iut_unilim.erp_back.dto.HistoryResponse;
import fr.iut_unilim.erp_back.dto.ResourceSheetRequest;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.repository.*;
import fr.iut_unilim.erp_back.service.ResourceSheetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/resourceSheet")
public class ResourceSheetController {

    private final ResourceSheetService resourceSheetService;
    private final ResourceSheetRepository resourceSheetRepository;
    private final ResourceRepository resourceRepository;
    private final ClassTypeRepository classTypeRepository;

    public ResourceSheetController(ResourceSheetService resourceSheetService, ResourceSheetRepository resourceSheetRepository, ResourceRepository resourceRepository, ClassTypeRepository classTypeRepository) {
        this.resourceSheetService = resourceSheetService;
        this.resourceSheetRepository = resourceSheetRepository;
        this.resourceRepository = resourceRepository;
        this.classTypeRepository = classTypeRepository;
    }

    @GetMapping("/getResourceSheet")
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public ResponseEntity<?> getResourceSheet() {
        return ResponseEntity.ok(resourceSheetService.getAllResourceSheets());
    }

    @PostMapping("/resource-sheet")
    public ResponseEntity<?> saveResourceSheet(@RequestBody ResourceSheetRequest resourceSheetRequest) {
        ResourceSheet resourceSheet = new ResourceSheet();

        Long resourceID = resourceSheetRequest.getResourceID();
        if(resourceID==null){
            return ResponseEntity.badRequest().body("resourceID is null");
        }
        ErpBackApplication.LOGGER.info("Saving resource sheet with id " + resourceSheetRequest.getResourceID());

        resourceSheet.setResourceID(resourceID);


        Long hourlyVolumeID = resourceSheetRequest.getHourlyVolumeID();
        if(hourlyVolumeID==null){
            return ResponseEntity.badRequest().body("HourlyVolumeID is null");
        }
        ErpBackApplication.LOGGER.info("Saving resource sheet with id " + resourceSheetRequest.getResourceID());

        resourceSheet.setHourlyVolumeID(hourlyVolumeID);

        List<String> teacherFeedbackID = resourceSheetRequest.getTeachersFeedbackID();
        if(teacherFeedbackID==null){
            return ResponseEntity.badRequest().body("TeacherFeedbackID is null");
        }
        List<PedagologicalTeachersFeedbacks> pedagologicalTeachersFeedbacks = new ArrayList<>();
        for(String teacherFeedback : teacherFeedbackID){
            PedagologicalTeachersFeedbacks pedagologicalTeachersFeedback = new PedagologicalTeachersFeedbacks();
            pedagologicalTeachersFeedback.setContent(teacherFeedback);
            pedagologicalTeachersFeedbacks.add(pedagologicalTeachersFeedback);
        }
        ErpBackApplication.LOGGER.info("Saving resource sheet with id " + resourceSheetRequest.getResourceID());        resourceSheet.setTeachersFeedbacks(pedagologicalTeachersFeedbacks);

        List<String> studentFeedbackID = resourceSheetRequest.getStudentFeedbackID();
        if(studentFeedbackID==null){
            return ResponseEntity.badRequest().body("TeacherFeedbackID is null");
        }
        List<StudentsFeedbacks> studentsFeedbacks = new ArrayList<>();
        for(String studentFeedback : studentFeedbackID){
            StudentsFeedbacks studentFeedbacks = new StudentsFeedbacks();
            studentFeedbacks.setContent(studentFeedback);
            studentsFeedbacks.add(studentFeedbacks);
        }
        ErpBackApplication.LOGGER.info("Saving resource sheet with id " + resourceSheetRequest.getResourceID());        resourceSheet.setStudentsFeedbacks(studentsFeedbacks);

        List<String> improvementIdeaId = resourceSheetRequest.getImprovementsIdeaID();
        if(improvementIdeaId==null){
            return ResponseEntity.badRequest().body("TeacherFeedbackID is null");
        }
        List<ImprovementIdeas> improvementsIdeas = new ArrayList<>();
        for(String improvementIdea : improvementIdeaId){
            ImprovementIdeas improvementIdeas = new ImprovementIdeas();
            improvementIdeas.setIdea(improvementIdea);
            improvementsIdeas.add(improvementIdeas);
        }
        ErpBackApplication.LOGGER.info("Saving resource sheet with id " + resourceSheetRequest.getResourceID());        resourceSheet.setImprovementIdeas(improvementsIdeas);

        List<PedagologicalContent> pedagologicalContent = new ArrayList<>();

        String regex = "^(TP|CM|TD)\\s*(\\d+)\\s*:\\s*(.*)$";
        Pattern pattern = Pattern.compile(regex);

        for (String educationalContent : resourceSheetRequest.getPedagologicalContent()) {
            Matcher matcher = pattern.matcher(educationalContent);

            if (!matcher.find()) {
                return ResponseEntity.badRequest().body("Invalid content !");
            }

            String typeName = matcher.group(1);
            String numero = matcher.group(2);
            String description = matcher.group(3);

            PedagologicalContent pedagologicalContentEntity = new PedagologicalContent();

            ClassType existingType = classTypeRepository.findByClassType(typeName)
                    .orElseThrow(() -> new RuntimeException("Erreur : Le type '" + typeName + "' n'existe pas."));
            pedagologicalContentEntity.setClassTypeId(existingType);

            pedagologicalContentEntity.setContent(description);
            pedagologicalContentEntity.setCourseNumber(Long.valueOf(numero));

            pedagologicalContentEntity.setRessourceSheetId(resourceSheet);

            pedagologicalContent.add(pedagologicalContentEntity);
        }
        resourceSheet.setPedagologicalContentId(pedagologicalContent);
        ErpBackApplication.LOGGER.info("Saving resource sheet with id " + resourceSheetRequest.getResourceID());        resourceSheet.setPedagologicalContentId(pedagologicalContent);


        resourceSheet.setCreationDate(null);

        resourceSheet.setLastModificationDate(null);

        resourceSheetService.save(resourceSheet);

        return ResponseEntity.ok("Fiche ressource sauvegardée avec succès !");
    }




    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> deleteResourceSheet(@PathVariable Long id) {
        if (!resourceSheetRepository.existsById(id)) {
            return ResponseEntity.ok().build();
        }
        resourceSheetRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getHistory")
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public ResponseEntity<List<HistoryResponse>> getHistory() {
        List<ResourceSheet> sheets = resourceSheetRepository.findAll();
        List<HistoryResponse> historyList = new ArrayList<>();

        for (ResourceSheet sheet : sheets) {
            String code = "Inconnu";
            String name = "Inconnue";

            if (sheet.getResourceID() != null) {
                Optional<Resource> res = resourceRepository.findById(sheet.getResourceID());
                if (res.isPresent()) {
                    code = res.get().getNum();
                    name = res.get().getName();
                }
            }

            Date dateToUse = sheet.getLastModificationDate() != null ?
                    sheet.getLastModificationDate() : sheet.getCreationDate();

            historyList.add(new HistoryResponse(
                    sheet.getSheetsID(),
                    code,
                    name,
                    dateToUse            ));
        }

        Collections.reverse(historyList);

        return ResponseEntity.ok(historyList);
    }
}