package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.CriticalLearning;

public record CriticalLearningDtos(int num, String intitule) {
    public CriticalLearningDtos(CriticalLearning criticalLearning) {
        this(criticalLearning.getLearningNum(), criticalLearning.getLearningTitle());
    }
}
