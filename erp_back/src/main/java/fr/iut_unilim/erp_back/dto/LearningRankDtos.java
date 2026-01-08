package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.Rank;

import java.util.List;

public record LearningRankDtos(int num, String intitule, List<CriticalLearningDtos> acs) {
    public LearningRankDtos(Rank rank, List<CriticalLearningDtos> acs) {
        this(rank.getRankNum(), rank.getRankTitle(), acs);
    }
}
