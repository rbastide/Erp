package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.CriticalConcept;

public record CriticalConceptDto(Long id, int num, String title) {
    public CriticalConceptDto(CriticalConcept criticalConcept) {
        this(criticalConcept.getCriticalConceptID(), criticalConcept.getCriticalConceptNum(), criticalConcept.getCriticalConceptTitle());
    }
}
