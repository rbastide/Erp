package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.CriticalLearning;
import fr.iut_unilim.erp_back.repository.CriticalLearningRepository;
import org.springframework.stereotype.Service;


@Service

public class CriticalLearningService {

    private final CriticalLearningRepository criticalLearningRepository;

    public CriticalLearningService(CriticalLearningRepository criticalLearningRepository) {this.criticalLearningRepository = criticalLearningRepository;}

    public void save(CriticalLearning criticalLearning) {
        criticalLearningRepository.save(criticalLearning);
    }
}
