package fr.iut_unilim.erp_back.model;

import org.jetbrains.annotations.NotNull;

public record CriticalLearningModel(int learningNum, String learningTitle) {
    @NotNull
    @Override
    public String toString() {
        return "CriticalLearning{" +
                "learningNum=" + learningNum +
                ", learningTitle='" + learningTitle + '\'' +
                '}';
    }
}
