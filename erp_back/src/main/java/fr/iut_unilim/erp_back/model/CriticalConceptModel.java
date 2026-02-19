package fr.iut_unilim.erp_back.model;

import org.jetbrains.annotations.NotNull;

public record CriticalConceptModel(int conceptNum, String conceptTitle) {
    @NotNull
    @Override
    public String toString() {
        return "CriticalConcept{" +
                "conceptNum=" + conceptNum +
                ", conceptTitle='" + conceptTitle + '\'' +
                '}';
    }
}
