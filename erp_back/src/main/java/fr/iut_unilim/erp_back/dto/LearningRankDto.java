package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.Rank;

import java.util.List;

public record LearningRankDto(Long id, int num, String title, List<CriticalLearningDto> acs) {
    public LearningRankDto(Rank rank, List<CriticalLearningDto> acs) {
        this(rank.getRankID(), rank.getRankNum(), rank.getRankTitle(), acs);
    }
}
