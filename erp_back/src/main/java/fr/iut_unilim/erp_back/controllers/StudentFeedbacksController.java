package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.service.StudentsFeedbacksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studentFeedbacks")
public class StudentFeedbacksController {

    private final StudentsFeedbacksService studentsFeedbacksService;

    public StudentFeedbacksController(StudentsFeedbacksService studentsFeedbacksService) {
        this.studentsFeedbacksService = studentsFeedbacksService;
    }

    @GetMapping("/getAllStudentFeedbacks")
    public ResponseEntity<?> getAllStudentFeedbacks() {
        return ResponseEntity.ok(studentsFeedbacksService.findAll());
    }
}
