package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.StudentsFeedbacks;
import fr.iut_unilim.erp_back.repository.StudentsFeedbacksRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentsFeedbacksService {

    private final StudentsFeedbacksRepository studentsFeedbacksRepository;

    public StudentsFeedbacksService(StudentsFeedbacksRepository studentsFeedbacksRepository) {
        this.studentsFeedbacksRepository = studentsFeedbacksRepository;
    }

    public Optional<StudentsFeedbacks> getStudentFeedbacksFromId(Long id) {
        return studentsFeedbacksRepository.findById(id);
    }

    public List<StudentsFeedbacks> getAll() {
        return studentsFeedbacksRepository.findAll();
    }
}
