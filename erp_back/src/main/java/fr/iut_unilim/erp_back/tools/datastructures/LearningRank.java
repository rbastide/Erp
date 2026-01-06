package fr.iut_unilim.erp_back.tools.datastructures;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record LearningRank(String resourceCode, String ue, String niveau, List<CriticalLearning> acs) {
    @NotNull
    @Override
    public String toString() {
        return "LearningRank{" +
                "resourceCode='" + resourceCode + '\'' +
                ", ue='" + ue + '\'' +
                ", niveau='" + niveau + '\'' +
                ", acs=" + acs +
                '}';
    }
}
