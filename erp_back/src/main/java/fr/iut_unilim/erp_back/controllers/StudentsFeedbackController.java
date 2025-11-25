package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.entity.StudentsFeedback;
import fr.iut_unilim.erp_back.service.StudentsFeedbackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/studentsFeedback")
public class StudentsFeedbackController {

    private StudentsFeedbackService studentsFeedbackService;

    public StudentsFeedbackController(StudentsFeedbackService studentsFeedbackService) {
        this.studentsFeedbackService = studentsFeedbackService;
    }
}
