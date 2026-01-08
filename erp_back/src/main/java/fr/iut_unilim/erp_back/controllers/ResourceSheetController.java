package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.ResourceSheetRequest;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.repository.*;
import fr.iut_unilim.erp_back.service.ResourceSheetService;
import fr.iut_unilim.erp_back.service.SaeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/resourceSheet")
public class ResourceSheetController {

    private final ResourceSheetService resourceSheetService;
    private final ResourceSheetRepository resourceSheetRepository;
    private final SaeRepository saeRepository;
    private final ResourceRepository resourceRepository;
    private final TeacherRepository  teacherRepository;
    private final HourlyVolumeRepository  hourlyVolumeRepository;
    private final PedagologicalTeachersFeedbacksRepository pedagologicalTeachersFeedbacksRepository;
    private final StudentsFeedbacksRepository studentsFeedbacksRepository;
    private final ImprovementIdeasRepository improvementIdeasRepository;

    public ResourceSheetController(ResourceSheetService resourceSheetService, ResourceSheetRepository resourceSheetRepository, SaeRepository saeRepository, ResourceRepository resourceRepository, TeacherRepository teacherRepository, HourlyVolumeRepository hourlyVolumeRepository, PedagologicalTeachersFeedbacksRepository pedagologicalTeachersFeedbacksRepository, StudentsFeedbacksRepository studentsFeedbacksRepository, ImprovementIdeasRepository improvementIdeasRepository) {
        this.resourceSheetService = resourceSheetService;
        this.resourceSheetRepository = resourceSheetRepository;
        this.saeRepository = saeRepository;
        this.resourceRepository = resourceRepository;
        this.teacherRepository = teacherRepository;
        this.hourlyVolumeRepository = hourlyVolumeRepository;
        this.pedagologicalTeachersFeedbacksRepository = pedagologicalTeachersFeedbacksRepository;
        this.studentsFeedbacksRepository = studentsFeedbacksRepository;
        this.improvementIdeasRepository = improvementIdeasRepository;
    }

    @GetMapping("/getResourceSheet")
    public ResponseEntity<?> getResourceSheet() {
        return ResponseEntity.ok(resourceSheetService.getAllResourceSheets());
    }

    @PostMapping("/resourceSheet")
    public ResponseEntity<?> editResourceSheet(@RequestBody List<ResourceSheetRequest> resSheetList) {

        for (ResourceSheetRequest resSheet : resSheetList) {

            Optional<ResourceSheet> existingResourceSheet = resourceSheetRepository.findById(resSheet.getSheetsID());
            if(existingResourceSheet.isPresent()) {
                ResourceSheet resourceSheetToUpdate = existingResourceSheet.get();
                Setter(resSheet, resourceSheetToUpdate);
            }
            else{
                ResourceSheet newResourceSheet = new ResourceSheet();
                Setter(resSheet, newResourceSheet);
            }
        }
        return ResponseEntity.ok().body("La fiche ressource a été ajouté");
    }

    private void Setter(ResourceSheetRequest resSheet, ResourceSheet sheet) {
        sheet.setSemester(resSheet.getSemester());
        sheet.setYear(resSheet.getYear());
        sheet.setMainGoal(resSheet.getMainGoal());
        sheet.setContent(resSheet.getContent());
        sheet.setCreationDate(resSheet.getCreationDate());
        sheet.setLastModificationDate(resSheet.getLastModificationDate());

        if (resSheet.getResourceID() != null) {
            Optional<Resource> existing = resourceRepository.findById(resSheet.getResourceID());
            if (existing.isPresent()) {
                sheet.setResourceID(existing.get().getResourceID());
            } else {
                Resource newObj = new Resource();
                newObj.setName("Ressource Auto " + resSheet.getResourceID());
                newObj.setNum("R" + resSheet.getResourceID());
                newObj = resourceRepository.save(newObj);
                sheet.setResourceID(newObj.getResourceID());
            }
        } else {
            sheet.setResourceID(null);
        }

        if (resSheet.getReferencialTeacherID() != null) {
            Optional<Teacher> existing = teacherRepository.findById(resSheet.getReferencialTeacherID());
            if (existing.isPresent()) {
                sheet.setReferencialTeacherID(existing.get().getId());
            } else {
                Teacher newObj = new Teacher();
                newObj.setFirstname("Auto");
                newObj.setLastname("Prof " + resSheet.getReferencialTeacherID());
                newObj = teacherRepository.save(newObj);
                sheet.setReferencialTeacherID(newObj.getId());
            }
        } else {
            sheet.setReferencialTeacherID(null);
        }

        if (resSheet.getLinkedSaeID() != null) {
            Optional<Sae> existing = saeRepository.findById(resSheet.getLinkedSaeID());
            if (existing.isPresent()) {
                sheet.setLinkedSAE(existing.get().getSaeID().intValue());
            } else {
                Sae newObj = new Sae();
                newObj.setNum("S" + resSheet.getLinkedSaeID());
                newObj.setTitle("SAE Auto");
                newObj = saeRepository.save(newObj);
                sheet.setLinkedSAE(newObj.getSaeID().intValue());
            }
        } else {
            sheet.setLinkedSAE(null);
        }
        if (resSheet.getHourlyVolumeID() != null) {
            Optional<HourlyVolume> existing = hourlyVolumeRepository.findById(resSheet.getHourlyVolumeID());
            if (existing.isPresent()) {
                sheet.setHourlyVolumeID(existing.get().hourlyVolID());
            } else {
                HourlyVolume newObj = new HourlyVolume();
                newObj.setNbHoursCM(0);
                newObj.setNbHoursTD(0);
                newObj.setNbHoursTP(0);
                newObj.setNbHoursDSTP(0);
                newObj.setNbHoursDS(0);
                newObj = hourlyVolumeRepository.save(newObj);
                sheet.setHourlyVolumeID(newObj.hourlyVolID());
            }
        } else {
            sheet.setHourlyVolumeID(null);
        }

        if (resSheet.getTeachersFeedbackID() != null) {
            Optional<PedagologicalTeachersFeedbacks> existing = pedagologicalTeachersFeedbacksRepository.findById(resSheet.getTeachersFeedbackID());
            if (existing.isPresent()) {
                sheet.setTeachersFeedbackID(existing.get().teachersFeedbackID());
            } else {
                PedagologicalTeachersFeedbacks newObj = new PedagologicalTeachersFeedbacks();
                newObj.setContent("Feedback Auto");
                newObj = pedagologicalTeachersFeedbacksRepository.save(newObj);
                sheet.setTeachersFeedbackID(newObj.teachersFeedbackID());
            }
        } else {
            sheet.setTeachersFeedbackID(null);
        }

        if (resSheet.getStudentFeedbackID() != null) {
            Optional<StudentsFeedbacks> existing = studentsFeedbacksRepository.findById(resSheet.getStudentFeedbackID());
            if (existing.isPresent()) {
                sheet.setStudentFeedbackID(existing.get().studentsFeedbackID());
            } else {
                StudentsFeedbacks newObj = new StudentsFeedbacks();
                newObj.setContent("Feedback Etudiant Auto");
                newObj = studentsFeedbacksRepository.save(newObj);
                sheet.setStudentFeedbackID(newObj.studentsFeedbackID());
            }
        } else {
            sheet.setStudentFeedbackID(null);
        }

        if (resSheet.getImprovementsIdeaID() != null) {
            Optional<ImprovementIdeas> existing = improvementIdeasRepository.findById(resSheet.getImprovementsIdeaID());
            if (existing.isPresent()) {
                sheet.setImprovementsIDeaID(existing.get().improvementsIdeaID());
            } else {
                ImprovementIdeas newObj = new ImprovementIdeas();
                newObj.setIdea("Idée Auto");
                newObj = improvementIdeasRepository.save(newObj);
                sheet.setImprovementsIDeaID(newObj.improvementsIdeaID());
            }
        } else {
            sheet.setImprovementsIDeaID(null);
        }

        resourceSheetRepository.save(sheet);
    }
}