package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.Rank;

import java.util.List;

public record LearningRankDto(Long id, int num, String title, List<CriticalConceptDto> acs) {
    public LearningRankDto(Rank rank, List<CriticalConceptDto> acs) {
        this(rank.getRankID(), rank.getRankNum(), rank.getRankTitle(), acs);
    }
}
