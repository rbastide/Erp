package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.StudentFeedbackResponse;
import fr.iut_unilim.erp_back.entity.StudentsFeedbacks;
import fr.iut_unilim.erp_back.repository.StudentsFeedbacksRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/studentFeedbacks")
public class StudentFeedbacksController {

    private final StudentsFeedbacksRepository studentsFeedbacksRepository;

    public StudentFeedbacksController(StudentsFeedbacksRepository studentsFeedbacksRepository) {
        this.studentsFeedbacksRepository = studentsFeedbacksRepository;
    }

    @GetMapping("/getAllStudentFeedbacks")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    public List<StudentFeedbackResponse> getAll() {
        List<StudentsFeedbacks> studentsFeedbacks = studentsFeedbacksRepository.findAll();
        return convertStudentFeedbacksEntitiesToDtos(studentsFeedbacks);
    }

    private List<StudentFeedbackResponse> convertStudentFeedbacksEntitiesToDtos(List<StudentsFeedbacks> studentsFeedbacks) {
        List<StudentFeedbackResponse> studentFeedbackResponses = new ArrayList<>();

        for (StudentsFeedbacks studentsFeedback : studentsFeedbacks) {
            StudentFeedbackResponse StudentFeedbackResponses = new StudentFeedbackResponse();

            StudentFeedbackResponses.setStudentFeedbackID(studentsFeedback.getStudentFeedbackID());
            StudentFeedbackResponses.setContent(studentsFeedback.getContent());

            studentFeedbackResponses.add(StudentFeedbackResponses);
        }

        return studentFeedbackResponses;

    }
}
