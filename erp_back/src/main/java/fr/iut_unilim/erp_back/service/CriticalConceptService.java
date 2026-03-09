package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.CriticalConcept;
import fr.iut_unilim.erp_back.entity.Rank;
import fr.iut_unilim.erp_back.repository.CriticalConceptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

public class CriticalConceptService {

    private final CriticalConceptRepository criticalConceptRepository;

    public CriticalConceptService(CriticalConceptRepository criticalConceptRepository) {
        this.criticalConceptRepository = criticalConceptRepository;
    }

    public List<CriticalConcept> getCriticalConceptWithNumAndTitleAndRank(int learningNum, String learningTitle, Rank rankID) {
        return criticalConceptRepository.findByCriticalConceptNumAndCriticalConceptTitleAndRankID(learningNum, learningTitle, rankID);
    }

    public Optional<CriticalConcept> getCriticalConceptFromId(Long id) {
        return criticalConceptRepository.findById(id);
    }
}
