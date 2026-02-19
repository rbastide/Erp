package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.TeachersEducationalFeedback;
import fr.iut_unilim.erp_back.repository.EducationalTeachersFeedbacksRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EducationalTeachersFeedbacksService {

    private final EducationalTeachersFeedbacksRepository educationalTeachersFeedbacksRepository;

    public EducationalTeachersFeedbacksService(EducationalTeachersFeedbacksRepository educationalTeachersFeedbacksRepository) {
        this.educationalTeachersFeedbacksRepository = educationalTeachersFeedbacksRepository;
    }

    public Optional<TeachersEducationalFeedback> findById(@NotNull Long id) {
        return educationalTeachersFeedbacksRepository.findById(id);
    }
}
