package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.ResourceSheetRequest;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.repository.*;
import fr.iut_unilim.erp_back.service.ResourceSheetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import fr.iut_unilim.erp_back.dto.HistoryResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/resourceSheet")
public class ResourceSheetController {

    private final ResourceSheetService resourceSheetService;
    private final ResourceSheetRepository resourceSheetRepository;
    private final SaeRepository saeRepository;
    private final ResourceRepository resourceRepository;
    private final TeacherRepository teacherRepository;
    private final HourlyVolumeRepository hourlyVolumeRepository;
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
            ResourceSheet sheetToSave;
            if (resSheet.getSheetsID() != null) {
                sheetToSave = resourceSheetRepository.findById(resSheet.getSheetsID())
                        .orElse(new ResourceSheet());
            } else {
                sheetToSave = new ResourceSheet();
            }
            Setter(resSheet, sheetToSave);
        }
        return ResponseEntity.ok().body("La fiche ressource a été sauvegardée avec succès");
    }

    private void Setter(ResourceSheetRequest dto, ResourceSheet sheet) {
        sheet.setSemester(dto.getSemester());
        sheet.setYear(dto.getYear());
        sheet.setMainGoal(dto.getMainGoal());
        sheet.setContent(dto.getContent());

        if (dto.getCreationDate() != null) {
            sheet.setCreationDate(dto.getCreationDate());
        } else if (sheet.getCreationDate() == null) {
            sheet.setCreationDate(new Date());
        }
        sheet.setLastModificationDate(new Date());

        if (dto.getResourceID() != null) {
            resourceRepository.findById(dto.getResourceID())
                    .ifPresent(r -> sheet.setResourceID(r.getResourceID()));
        } else {
            sheet.setResourceID(null);
        }

        if (dto.getLinkedSaeID() != null) {
            saeRepository.findById(dto.getLinkedSaeID())
                    .ifPresent(s -> sheet.setLinkedSAE(s.getSaeID().intValue()));
        } else {
            sheet.setLinkedSAE(null);
        }

        if (dto.getReferencialTeacherID() != null) {
            teacherRepository.findById(dto.getReferencialTeacherID())
                    .ifPresent(t -> sheet.setReferencialTeacherID(t.getTeacherID()));
        } else {
            sheet.setReferencialTeacherID(null);
        }

        HourlyVolume volume;
        if (dto.getHourlyVolumeID() != null) {
            volume = hourlyVolumeRepository.findById(dto.getHourlyVolumeID())
                    .orElse(new HourlyVolume());
        } else {
            volume = new HourlyVolume();
        }
        volume.setNbHoursCM(dto.getHoursCM());
        volume.setNbHoursTD(dto.getHoursTD());
        volume.setNbHoursTP(dto.getHoursTP());
        volume.setNbHoursDS(dto.getHoursDS());
        volume.setNbHoursDSTP(dto.getHoursDSTP());

        volume = hourlyVolumeRepository.save(volume);
        sheet.setHourlyVolumeID(volume.getHourlyVolID());

        PedagologicalTeachersFeedbacks tFeedback;
        if (dto.getTeachersFeedbackID() != null) {
            tFeedback = pedagologicalTeachersFeedbacksRepository.findById(dto.getTeachersFeedbackID())
                    .orElse(new PedagologicalTeachersFeedbacks());
        } else {
            tFeedback = new PedagologicalTeachersFeedbacks();
        }
        tFeedback.setContent(dto.getTeacherFeedbackContent());
        tFeedback = pedagologicalTeachersFeedbacksRepository.save(tFeedback);
        sheet.setTeachersFeedbackID(tFeedback.teachersFeedbackID());

        StudentsFeedbacks sFeedback;
        if (dto.getStudentFeedbackID() != null) {
            sFeedback = studentsFeedbacksRepository.findById(dto.getStudentFeedbackID())
                    .orElse(new StudentsFeedbacks());
        } else {
            sFeedback = new StudentsFeedbacks();
        }
        sFeedback.setContent(dto.getStudentFeedbackContent());
        sFeedback = studentsFeedbacksRepository.save(sFeedback);
        sheet.setStudentFeedbackID(sFeedback.studentsFeedbackID());

        ImprovementIdeas ideas;
        if (dto.getImprovementsIdeaID() != null) {
            ideas = improvementIdeasRepository.findById(dto.getImprovementsIdeaID())
                    .orElse(new ImprovementIdeas());
        } else {
            ideas = new ImprovementIdeas();
        }
        ideas.setIdea(dto.getImprovementIdeaContent());
        ideas = improvementIdeasRepository.save(ideas);
        sheet.setImprovementsIDeaID(ideas.improvementsIdeaID());

        resourceSheetRepository.save(sheet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResourceSheet(@PathVariable Long id) {
        if (!resourceSheetRepository.existsById(id)) {
            return ResponseEntity.ok().build();
        }
        resourceSheetRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getHistory")
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
                    dateToUse
            ));
        }

        Collections.reverse(historyList);

        return ResponseEntity.ok(historyList);
    }
}