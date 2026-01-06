package fr.iut_unilim.erp_back.tools.datastructures;

import org.jetbrains.annotations.NotNull;

public record CriticalLearning(int learningNum, String learningTitle) {
    @NotNull
    @Override
    public String toString() {
        return "CriticalLearning{" +
                "learningNum=" + learningNum +
                ", learningTitle='" + learningTitle + '\'' +
                '}';
    }
}
