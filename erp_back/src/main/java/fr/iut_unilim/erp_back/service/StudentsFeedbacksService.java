package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.StudentsFeedbacks;
import fr.iut_unilim.erp_back.repository.StudentsFeedbacksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentsFeedbacksService {

    private final StudentsFeedbacksRepository studentsFeedbacksRepository;

    public StudentsFeedbacksService(StudentsFeedbacksRepository studentsFeedbacksRepository) {
        this.studentsFeedbacksRepository = studentsFeedbacksRepository;
    }

    public List<StudentsFeedbacks> findAll() {
        return studentsFeedbacksRepository.findAll();
    }
}
