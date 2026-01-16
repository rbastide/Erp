package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.CriticalLearning;

public record CriticalLearningDto(Long id, int num, String title) {
    public CriticalLearningDto(CriticalLearning criticalLearning) {
        this(criticalLearning.getCriticalLearningID(), criticalLearning.getCriticalLearningNum(), criticalLearning.getCriticalLearningTitle());
    }
}
