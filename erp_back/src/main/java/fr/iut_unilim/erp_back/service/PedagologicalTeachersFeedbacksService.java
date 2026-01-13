package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.PedagologicalTeachersFeedbacks;
import fr.iut_unilim.erp_back.repository.PedagologicalTeachersFeedbacksRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedagologicalTeachersFeedbacksService {

    private final PedagologicalTeachersFeedbacksRepository pedagologicalTeachersFeedbacksRepository;

    public PedagologicalTeachersFeedbacksService(PedagologicalTeachersFeedbacksRepository pedagologicalTeachersFeedbackRepository) {
        this.pedagologicalTeachersFeedbacksRepository = pedagologicalTeachersFeedbackRepository;
    }

    public Optional<PedagologicalTeachersFeedbacks> findById(@NotNull Long id) {
        return pedagologicalTeachersFeedbacksRepository.findById(id);
    }
}
