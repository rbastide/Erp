package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.repository.StudentsFeedbacksRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentsFeedbacksService {

    private final StudentsFeedbacksRepository studentsFeedbackRepository;

    public StudentsFeedbacksService(StudentsFeedbacksRepository studentsFeedbackRepository) {
        this.studentsFeedbackRepository = studentsFeedbackRepository;
    }
}
