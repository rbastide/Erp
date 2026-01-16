package fr.iut_unilim.erp_back.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record LearningRankModel(String resourceCode, String ue, String levels, List<CriticalLearningModel> acs) {
    @NotNull
    @Override
    public String toString() {
        return "LearningRank{" +
                "resourceCode='" + resourceCode + '\'' +
                ", ue='" + ue + '\'' +
                ", levels='" + levels + '\'' +
                ", acs=" + acs +
                '}';
    }
}
