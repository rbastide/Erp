package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.ResourceSheetRequest;
import fr.iut_unilim.erp_back.entity.ClassType;
import fr.iut_unilim.erp_back.entity.CourseHours;
import fr.iut_unilim.erp_back.entity.EducationalContent;
import fr.iut_unilim.erp_back.entity.ResourceSheet;
import fr.iut_unilim.erp_back.repository.ClassTypeRepository;
import fr.iut_unilim.erp_back.repository.ResourceSheetRepository;
import fr.iut_unilim.erp_back.service.CourseHoursService;
import fr.iut_unilim.erp_back.service.ResourceSheetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/resourceSheet")
@CrossOrigin(origins = "http://localhost:5173")
public class ResourceSheetController {

    private final ResourceSheetService resourceSheetService;
    private final ResourceSheetRepository resourceSheetRepository;
    private final CourseHoursService courseHoursService;
    private final ClassTypeRepository classTypeRepository;

    public ResourceSheetController(ResourceSheetService resourceSheetService, ResourceSheetRepository resourceSheetRepository, CourseHoursService courseHoursService, ClassTypeRepository classTypeRepository) {
        this.resourceSheetService = resourceSheetService;
        this.resourceSheetRepository = resourceSheetRepository;
        this.courseHoursService = courseHoursService;
        this.classTypeRepository = classTypeRepository;
    }

    @GetMapping("/getResourceSheet")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> getResourceSheet(Authentication authentication) {
        return ResponseEntity.ok(resourceSheetService.getAllResourceSheetsFromDepartment(authentication.getName()));
    }

    @PostMapping("/resource-sheet")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> saveResourceSheet(@RequestBody ResourceSheetRequest resourceSheetRequest, Authentication authentication) {
        ResourceSheet resourceSheet;
        if (resourceSheetRequest.resourceSheetID() == null) {
            resourceSheet = new ResourceSheet();
            resourceSheet.setCreationDate(new Date());
        } else {
            Optional<ResourceSheet> existingResourceSheet = resourceSheetRepository.findById(resourceSheetRequest.resourceSheetID());
            if (existingResourceSheet.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            resourceSheet = existingResourceSheet.get();
        }

        resourceSheet.setLastModificationDate(new Date());
        resourceSheet.setImprovementIdeas(resourceSheetRequest.improvementsIdeas());
        resourceSheet.setStudentFeedbacks(resourceSheetRequest.studentFeedbacks());
        resourceSheet.setTeacherFeedbacks(resourceSheetRequest.teacherFeedbacks());

        List<EducationalContent> educationalContents = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : resourceSheetRequest.educationalContents().entrySet()) {
            long currentCourseNumber = 1;
            Optional<ClassType> classType = classTypeRepository.findByClassTypeName(entry.getKey());
            if (classType.isEmpty())
                return ResponseEntity.badRequest().body("Le type de classe " + entry.getKey() + " n'existe pas");
            for (String content : entry.getValue()) {
                EducationalContent educationalContent = new EducationalContent();
                educationalContent.setResourceSheet(resourceSheet);
                educationalContent.setContent(content);
                educationalContent.setCourseNumber(currentCourseNumber);
                educationalContent.setClassType(classType.get());

                educationalContents.add(educationalContent);
                currentCourseNumber++;
            }
        }

        resourceSheet.setEducationalContentID(educationalContents);
        resourceSheet.setCourseHours(findOrCreateCourseHoursFromDtoRequest(resourceSheetRequest));

        resourceSheetRepository.save(resourceSheet);

        return ResponseEntity.ok().build();
    }

    private CourseHours findOrCreateCourseHoursFromDtoRequest(ResourceSheetRequest resourceSheetRequest) {
        float hoursCM = resourceSheetRequest.courseHours().get("cm");
        float hoursTD = resourceSheetRequest.courseHours().get("td");
        float hoursTP = resourceSheetRequest.courseHours().get("tp");
        float hoursDSTP = resourceSheetRequest.courseHours().get("ds_tp");
        float hoursDS = resourceSheetRequest.courseHours().get("ds");
        List<CourseHours> courseHours = courseHoursService.getAllCourseHoursFromDatas(hoursCM, hoursTD, hoursTP, hoursDSTP, hoursDS);
        if (courseHours.isEmpty()) {
            return new CourseHours(hoursCM, hoursDS, hoursDSTP, hoursTP, hoursTD);
        }
        return courseHours.get(0);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public ResponseEntity<?> deleteResourceSheet(@PathVariable Long id) {
        if (!resourceSheetRepository.existsById(id)) {
            return ResponseEntity.ok().build();
        }
        resourceSheetRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/getHistory")
//    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
//    public ResponseEntity<List<HistoryResponse>> getHistory(Authentication authentication) {
//        List<ResourceSheet> sheets = resourceSheetService.getAllResourceSheetsFromDepartment(authentication.getName());
//        List<HistoryResponse> historyList = new ArrayList<>();
//
//        for (ResourceSheet sheet : sheets) {
//            String code = "Inconnu";
//            String name = "Inconnue";
//
//            if (sheet.getResource() != null) {
//                Optional<Resource> res = resourceRepository.findById(sheet.getResource());
//                if (res.isPresent()) {
//                    code = res.get().getNum();
//                    name = res.get().getName();
//                }
//            }
//
//            Date dateToUse = sheet.getLastModificationDate() != null ?
//                    sheet.getLastModificationDate() : sheet.getCreationDate();
//
//            historyList.add(new HistoryResponse(
//                    sheet.getSheetID(),
//                    code,
//                    name,
//                    dateToUse));
//        }
//
//        Collections.reverse(historyList);
//
//        return ResponseEntity.ok(historyList);
//    }
}