package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.service.StudentsFeedbacksService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/studentsFeedbacks")
public class StudentsFeedbacksController {

    private StudentsFeedbacksService studentsFeedbackService;

    public StudentsFeedbacksController(StudentsFeedbacksService studentsFeedbackService) {
        this.studentsFeedbackService = studentsFeedbackService;
    }
}
