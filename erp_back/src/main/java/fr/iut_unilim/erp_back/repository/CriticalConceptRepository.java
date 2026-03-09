package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.CriticalConcept;
import fr.iut_unilim.erp_back.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriticalConceptRepository extends JpaRepository<CriticalConcept, Long> {
    List<CriticalConcept> findByCriticalConceptNum(int learningNum);

    List<CriticalConcept> findByCriticalConceptNumAndCriticalConceptTitleAndRankID(int learningNum, String learningTitle, Rank rankID);

    List<CriticalConcept> findByRankID(Rank rankID);
}
