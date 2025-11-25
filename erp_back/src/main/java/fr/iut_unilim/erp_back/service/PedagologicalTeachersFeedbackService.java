package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.repository.PedagologicalTeachersFeedbackRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PedagologicalTeachersFeedbackService {

    private final PedagologicalTeachersFeedbackRepository pedagologicalTeachersFeedbackRepository;

    public PedagologicalTeachersFeedbackService(PedagologicalTeachersFeedbackRepository pedagologicalTeachersFeedbackRepository) {
        this.pedagologicalTeachersFeedbackRepository = pedagologicalTeachersFeedbackRepository;
    }
}
