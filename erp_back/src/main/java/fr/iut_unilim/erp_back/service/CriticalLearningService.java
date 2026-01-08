package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.CriticalLearning;
import fr.iut_unilim.erp_back.entity.Rank;
import fr.iut_unilim.erp_back.repository.CriticalLearningRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class CriticalLearningService {

    private final CriticalLearningRepository criticalLearningRepository;

    public CriticalLearningService(CriticalLearningRepository criticalLearningRepository) {this.criticalLearningRepository = criticalLearningRepository;}

    public List<CriticalLearning> getCriticalLearningsWithNumAndTitleAndRank(int learningNum, String learningTitle, Rank rankID) {
        return criticalLearningRepository.findByLearningNumAndLearningTitleAndRankID(learningNum, learningTitle, rankID);
    }

    public List<CriticalLearning> getAllCriticalLearning() {
        return criticalLearningRepository.findAll();
    }

    public List<CriticalLearning> getCriticalLearningsByRank(Rank rankID) {
        return criticalLearningRepository.findByRankID(rankID);
    }
}
