package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.HistoryResponse;
import fr.iut_unilim.erp_back.dto.ResourceSheetRequest;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.repository.ResourceRepository;
import fr.iut_unilim.erp_back.repository.ResourceSheetRepository;
import fr.iut_unilim.erp_back.service.ConnectionService;
import fr.iut_unilim.erp_back.service.HourlyVolumeService;
import fr.iut_unilim.erp_back.service.ResourceSheetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resourceSheet")
@CrossOrigin(origins = "http://localhost:5173")
public class ResourceSheetController {

    private final ResourceSheetService resourceSheetService;
    private final ResourceSheetRepository resourceSheetRepository;
    private final ResourceRepository resourceRepository;
    private final ConnectionService connectionService;
    private final HourlyVolumeService hourlyVolumeService;

    public ResourceSheetController(ResourceSheetService resourceSheetService, ResourceSheetRepository resourceSheetRepository, ResourceRepository resourceRepository, ConnectionService connectionService, HourlyVolumeService hourlyVolumeService) {
        this.resourceSheetService = resourceSheetService;
        this.resourceSheetRepository = resourceSheetRepository;
        this.resourceRepository = resourceRepository;
        this.connectionService = connectionService;
        this.hourlyVolumeService = hourlyVolumeService;
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

        ResourceSheet resourceSheet;
        Long resourceSheetId = resourceSheetRequest.getSheetsID();

        if (resourceSheetId != null) {
            resourceSheet = resourceSheetRepository.findById(resourceSheetId)
                    .orElse(new ResourceSheet());
            if (resourceSheet.getSheetsID() == null) {
                resourceSheet.setCreationDate(new Date());
            }
        } else {
            resourceSheet = new ResourceSheet();
            resourceSheet.setCreationDate(new Date());
        }

        resourceSheet.setResourceID(resourceSheetRequest.getResourceID());
        resourceSheet.setLastModificationDate(new Date());

        if (connection != null) {
            resourceSheet.setUniversityDepartment(connection.getUniversityDepartment());
        }

        HourlyVolume hourlyVolume = new HourlyVolume();

        if (resourceSheetRequest.getHourlyVolume() != null) {
            hourlyVolume.setNbHoursTP(resourceSheetRequest.getHourlyVolume().getTp());
            hourlyVolume.setNbHoursDSTP(resourceSheetRequest.getHourlyVolume().getDs_tp());
            hourlyVolume.setNbHoursDS(resourceSheetRequest.getHourlyVolume().getDs());
            hourlyVolume.setNbHoursTD(resourceSheetRequest.getHourlyVolume().getTd());
            hourlyVolume.setNbHoursCM(resourceSheetRequest.getHourlyVolume().getCm());
        } else {
            hourlyVolume.setNbHoursTP(0F);
            hourlyVolume.setNbHoursDSTP(0F);
            hourlyVolume.setNbHoursDS(0F);
            hourlyVolume.setNbHoursTD(0F);
            hourlyVolume.setNbHoursCM(0F);
        }

        HourlyVolume savedVolume = hourlyVolumeService.save(hourlyVolume);
        resourceSheet.setHourlyVolumeID(savedVolume.getHourlyVolID());

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
            List<EducationalTeachersFeedbacks> teacherEntities = teacherContents.stream()
                    .filter(c -> c != null && !c.isBlank())
                    .map(content -> {
                        EducationalTeachersFeedbacks fb = new EducationalTeachersFeedbacks();
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
            List<ImprovementIdeas> ideaEntities = ideaContents.stream()
                    .filter(c -> c != null && !c.isBlank())
                    .map(content -> {
                        ImprovementIdeas idea = new ImprovementIdeas();
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

        resourceSheetService.save(resourceSheet);

        return ResponseEntity.ok("L'ajout des fiches ressources a été effectué avec succès");
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