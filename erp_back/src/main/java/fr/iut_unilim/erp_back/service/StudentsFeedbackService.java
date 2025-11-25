package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.repository.StudentsFeedbackRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentsFeedbackService {

    private final StudentsFeedbackRepository studentsFeedbackRepository;

    public StudentsFeedbackService(StudentsFeedbackRepository studentsFeedbackRepository) {
        this.studentsFeedbackRepository = studentsFeedbackRepository;
    }
}
