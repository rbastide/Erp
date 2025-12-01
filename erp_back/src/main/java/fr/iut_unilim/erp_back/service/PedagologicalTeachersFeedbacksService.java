package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.repository.PedagologicalTeachersFeedbacksRepository;
import org.springframework.stereotype.Service;

@Service
public class PedagologicalTeachersFeedbacksService {

    private final PedagologicalTeachersFeedbacksRepository pedagologicalTeachersFeedbackRepository;

    public PedagologicalTeachersFeedbacksService(PedagologicalTeachersFeedbacksRepository pedagologicalTeachersFeedbackRepository) {
        this.pedagologicalTeachersFeedbackRepository = pedagologicalTeachersFeedbackRepository;
    }
}
