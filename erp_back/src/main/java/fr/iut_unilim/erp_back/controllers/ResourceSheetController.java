package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.HistoryResponse;
import fr.iut_unilim.erp_back.dto.ResourceSheetRequest;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.repository.ClassTypeRepository;
import fr.iut_unilim.erp_back.repository.ResourceRepository;
import fr.iut_unilim.erp_back.repository.ResourceSheetRepository;
import fr.iut_unilim.erp_back.service.ConnectionService;
import fr.iut_unilim.erp_back.service.CourseHoursService;
import fr.iut_unilim.erp_back.service.ResourceSheetService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resourceSheet")
@CrossOrigin(origins = "http://localhost:5173")
public class ResourceSheetController {

    private final ResourceSheetService resourceSheetService;
    private final ResourceSheetRepository resourceSheetRepository;
    private final ResourceRepository resourceRepository;
    private final ConnectionService connectionService;
    private final CourseHoursService courseHoursService;
    private final ClassTypeRepository classTypeRepository;

    private static final Pattern EDUCATIONAL_CONTENT_PATTERN = Pattern.compile("^([A-Z/]+)\\s+(\\d+)\\s*:\\s*(.*)$");

    public ResourceSheetController(ResourceSheetService resourceSheetService, ResourceSheetRepository resourceSheetRepository, ResourceRepository resourceRepository, ConnectionService connectionService, CourseHoursService courseHoursService, ClassTypeRepository classTypeRepository) {
        this.resourceSheetService = resourceSheetService;
        this.resourceSheetRepository = resourceSheetRepository;
        this.resourceRepository = resourceRepository;
        this.connectionService = connectionService;
        this.courseHoursService = courseHoursService;
        this.classTypeRepository = classTypeRepository;
    }

    @GetMapping("/getResourceSheet")
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public ResponseEntity<?> getResourceSheet(Authentication authentication) {
        return ResponseEntity.ok(resourceSheetService.getAllResourceSheetsFromDepartment(authentication.getName()));
    }

    @PostMapping("/resource-sheet")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<?> saveResourceSheet(@RequestBody ResourceSheetRequest resourceSheetRequest, Authentication authentication) {

        Connection connection = connectionService.getConnectionByIdentifier(authentication.getName());

        Resource resource;
        ResourceSheet resourceSheet;
        Long targetResourceId = resourceSheetRequest.getResourceID();
        Long resourceSheetId = resourceSheetRequest.getSheetsID();

        if (targetResourceId != null && resourceSheetId != null) {
            resource = resourceRepository.findById(targetResourceId).orElse(new Resource());
                    resourceSheet = resourceSheetRepository.findById(resourceSheetId).orElse(new ResourceSheet());

            if (resource.getResourceID() == null) {
                resourceSheet.setCreationDate(new Date());
            }
        } else {
            resource = new Resource();
            resourceSheet = new ResourceSheet();
            resourceSheet.setCreationDate(new Date());
        }

        resourceSheet.setResourceID(resourceSheetRequest.getResourceID());
        resourceSheet.setLastModificationDate(new Date());

        if (connection != null) {
            resourceSheet.setUniversityDepartment(connection.getUniversityDepartment());
        }

        CourseHours courseHours = getCourseHours(resourceSheetRequest);

        CourseHours savedVolume = courseHoursService.save(courseHours);
        resourceSheet.setCourseHoursID(savedVolume);

        List<String> studentContents = resourceSheetRequest.getStudentFeedbackID();
        if (studentContents != null) {
            List<StudentsFeedbacks> studentEntities = studentContents.stream()
                    .filter(c -> c != null && !c.isBlank())
                    .map(content -> {
                        StudentsFeedbacks fb = new StudentsFeedbacks();
                        fb.setContent(content);
                        return fb;
                    })
                    .collect(Collectors.toList());

            if (resourceSheet.getStudentsFeedbacks() != null) {
                resourceSheet.getStudentsFeedbacks().clear();
                resourceSheet.getStudentsFeedbacks().addAll(studentEntities);
            } else {
                resourceSheet.setStudentsFeedbacks(studentEntities);
            }
        }

        List<String> teacherContents = resourceSheetRequest.getTeachersFeedbackID();
        if (teacherContents != null) {
            List<TeachersEducationalFeedback> teacherEntities = teacherContents.stream()
                    .filter(c -> c != null && !c.isBlank())
                    .map(content -> {
                        TeachersEducationalFeedback fb = new TeachersEducationalFeedback();
                        fb.setContent(content);
                        return fb;
                    })
                    .collect(Collectors.toList());

            if (resourceSheet.getTeachersFeedbacks() != null) {
                resourceSheet.getTeachersFeedbacks().clear();
                resourceSheet.getTeachersFeedbacks().addAll(teacherEntities);
            } else {
                resourceSheet.setTeachersFeedbacks(teacherEntities);
            }
        }
        List<String> ideaContents = resourceSheetRequest.getImprovementsIdeaID();
        if (ideaContents != null) {
            List<ImprovementIdea> ideaEntities = ideaContents.stream()
                    .filter(c -> c != null && !c.isBlank())
                    .map(content -> {
                        ImprovementIdea idea = new ImprovementIdea();
                        idea.setIdeaContent(content);
                        return idea;
                    })
                    .collect(Collectors.toList());

            if (resourceSheet.getImprovementIdeas() != null) {
                resourceSheet.getImprovementIdeas().clear();
                resourceSheet.getImprovementIdeas().addAll(ideaEntities);
            } else {
                resourceSheet.setImprovementIdeas(ideaEntities);
            }
        }

        List<String> educationalContentsStrings = resourceSheetRequest.getEducationalContent();
        if (educationalContentsStrings != null) {
            List<EducationalContent> educationalContentEntities = new ArrayList<>();

            for (String rawContent : educationalContentsStrings) {
                Matcher matcher = EDUCATIONAL_CONTENT_PATTERN.matcher(rawContent);

                if (matcher.find()) {
                    String typeStr = matcher.group(1);
                    String numStr = matcher.group(2);
                    String contentStr = matcher.group(3);

                    Optional<ClassType> classTypeOpt = classTypeRepository.findByClassTypeName(typeStr);

                    if (classTypeOpt.isPresent()) {
                        EducationalContent eduContent = new EducationalContent();
                        eduContent.setClassTypeId(classTypeOpt.get());
                        eduContent.setCourseNumber(Long.parseLong(numStr));
                        eduContent.setContent(contentStr);

                        eduContent.setResourceSheetId(resourceSheet);

                        educationalContentEntities.add(eduContent);
                    } else {
                        System.out.println("Attention: Type de cours inconnu en base de données : " + typeStr);
                    }
                }
            }
            if (resourceSheet.getEducationalContentID() != null) {
                resourceSheet.getEducationalContentID().clear();
                resourceSheet.getEducationalContentID().addAll(educationalContentEntities);
            } else {
                resourceSheet.setEducationalContentID(educationalContentEntities);
            }
        }

        

        resourceSheetService.save(resourceSheet);

        return ResponseEntity.ok("L'ajout des fiches ressources a été effectué avec succès");
    }

    @NotNull
    private static CourseHours getCourseHours(ResourceSheetRequest resourceSheetRequest) {
        CourseHours courseHours = new CourseHours();

        if (resourceSheetRequest.getCourseHours() != null) {
            courseHours.setNbHoursTP(resourceSheetRequest.getCourseHours().getTp());
            courseHours.setNbHoursDSTP(resourceSheetRequest.getCourseHours().getDs_tp());
            courseHours.setNbHoursDS(resourceSheetRequest.getCourseHours().getDs());
            courseHours.setNbHoursTD(resourceSheetRequest.getCourseHours().getTd());
            courseHours.setNbHoursCM(resourceSheetRequest.getCourseHours().getCm());
        } else {
            courseHours.setNbHoursTP(0F);
            courseHours.setNbHoursDSTP(0F);
            courseHours.setNbHoursDS(0F);
            courseHours.setNbHoursTD(0F);
            courseHours.setNbHoursCM(0F);
        }
        return courseHours;
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
    public ResponseEntity<List<HistoryResponse>> getHistory(Authentication authentication) {
        List<ResourceSheet> sheets = resourceSheetService.getAllResourceSheetsFromDepartment(authentication.getName());
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
                    dateToUse));
        }

        Collections.reverse(historyList);

        return ResponseEntity.ok(historyList);
    }
}