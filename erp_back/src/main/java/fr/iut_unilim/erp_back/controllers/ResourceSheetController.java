package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.HistoryResponse;
import fr.iut_unilim.erp_back.dto.ResourceSheetRequest;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.repository.ClassTypeRepository;
import fr.iut_unilim.erp_back.repository.ResourceRepository;
import fr.iut_unilim.erp_back.repository.ResourceSheetRepository;
import fr.iut_unilim.erp_back.service.ResourceSheetService;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/resourceSheet")
@CrossOrigin(origins = "http://localhost:5173")
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
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> saveResourceSheet(@RequestBody ResourceSheetRequest resourceSheetRequest) {

        ResourceSheet resourceSheet;
        Long resourceSheetID = resourceSheetRequest.getSheetsID();

        Optional<ResourceSheet> canHaveResourceSheet = resourceSheetRepository.findById(resourceSheetID);
        if (canHaveResourceSheet.isPresent()) {
            resourceSheet = canHaveResourceSheet.get();
            clearExistingContent(resourceSheet);
        } else {
            resourceSheet = new ResourceSheet();
            initDefaultValues(resourceSheet);
        }

        if (resourceSheetRequest.getResourceID() == null) return ResponseEntity.badRequest().body("resourceID is null");
        resourceSheet.setResourceID(resourceSheetRequest.getResourceID());

        if (resourceSheetRequest.getHourlyVolumeID() == null) return ResponseEntity.badRequest().body("HourlyVolumeID is null");
        resourceSheet.setHourlyVolumeID(resourceSheetRequest.getHourlyVolumeID());

        handleTeacherFeedbacks(resourceSheetRequest, resourceSheet);
        handleStudentFeedbacks(resourceSheetRequest, resourceSheet);
        handleImprovementIdeasFeedbacks(resourceSheetRequest, resourceSheet);

        ResponseEntity<String> educationalContent = handleEducationalContent(resourceSheetRequest, resourceSheet);
        if (educationalContent != null) return educationalContent;

        resourceSheet.setLastModificationDate(new Date());

        resourceSheetService.save(resourceSheet);

        return ResponseEntity.ok("Fiche ressource sauvegardée avec succès !");
    }

    private static void handleImprovementIdeasFeedbacks(ResourceSheetRequest resourceSheetRequest, ResourceSheet resourceSheet) {
        List<String> ideasReq = resourceSheetRequest.getImprovementsIdeaID();
        if (ideasReq != null) {
            for (String content : ideasReq) {
                ImprovementIdeas item = new ImprovementIdeas();
                item.setIdea(content);
                resourceSheet.getImprovementIdeas().add(item);
            }
        }
    }

    private static void handleStudentFeedbacks(ResourceSheetRequest resourceSheetRequest, ResourceSheet resourceSheet) {
        List<String> studentFeedbacksReq = resourceSheetRequest.getStudentFeedbackID();
        if (studentFeedbacksReq != null) {
            for (String content : studentFeedbacksReq) {
                StudentsFeedbacks item = new StudentsFeedbacks();
                item.setContent(content);
                resourceSheet.getStudentsFeedbacks().add(item);
            }
        }
    }

    private static void handleTeacherFeedbacks(ResourceSheetRequest resourceSheetRequest, ResourceSheet resourceSheet) {
        List<String> teacherFeedbacksReq = resourceSheetRequest.getTeachersFeedbackID();
        if (teacherFeedbacksReq != null) {
            for (String content : teacherFeedbacksReq) {
                PedagologicalTeachersFeedbacks item = new PedagologicalTeachersFeedbacks();
                item.setContent(content);
                resourceSheet.getTeachersFeedbacks().add(item);
            }
        }
    }

    private static void initDefaultValues(ResourceSheet resourceSheet) {
        resourceSheet.setCreationDate(new Date());

        resourceSheet.setTeachersFeedbacks(new ArrayList<>());
        resourceSheet.setStudentsFeedbacks(new ArrayList<>());
        resourceSheet.setImprovementIdeas(new ArrayList<>());
        resourceSheet.setPedagologicalContentId(new ArrayList<>());
    }

    private static void clearExistingContent(ResourceSheet resourceSheet) {
        if (resourceSheet.getTeachersFeedbacks() != null) resourceSheet.getTeachersFeedbacks().clear();
        if (resourceSheet.getStudentsFeedbacks() != null) resourceSheet.getStudentsFeedbacks().clear();
        if (resourceSheet.getImprovementIdeas() != null) resourceSheet.getImprovementIdeas().clear();
        if (resourceSheet.getPedagologicalContentId() != null) resourceSheet.getPedagologicalContentId().clear();
    }

    @Nullable
    private ResponseEntity<String> handleEducationalContent(ResourceSheetRequest resourceSheetRequest, ResourceSheet resourceSheet) {
        if (resourceSheetRequest.getPedagologicalContent() != null) {
            String regex = "^(TP|CM|TD|DS|DS/TP)\\s*(\\d+)\\s*:\\s*(.*)$";
            Pattern pattern = Pattern.compile(regex);

            for (String educationalContent : resourceSheetRequest.getPedagologicalContent()) {
                Matcher matcher = pattern.matcher(educationalContent);

                if (!matcher.find()) {
                    return ResponseEntity.badRequest().body("Contenu invalide : " + educationalContent);
                }

                String typeName = matcher.group(1);
                String numero = matcher.group(2);
                String description = matcher.group(3);

                PedagologicalContent contentEntity = new PedagologicalContent();

                ClassType existingType = classTypeRepository.findByClassType(typeName)
                        .orElseThrow(() -> new RuntimeException("Type introuvable : " + typeName));
                contentEntity.setClassTypeId(existingType);

                contentEntity.setCourseNumber(Long.valueOf(numero));
                contentEntity.setContent(description);
                contentEntity.setRessourceSheetId(resourceSheet);

                resourceSheet.getPedagologicalContentId().add(contentEntity);
            }
        }
        return null;
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