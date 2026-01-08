package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.CriticalLearning;
import fr.iut_unilim.erp_back.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriticalLearningRepository extends JpaRepository<CriticalLearning,Long> {
    List<CriticalLearning> findByLearningNum(int learningNum);

    List<CriticalLearning> findByLearningNumAndLearningTitleAndRankID(int learningNum, String learningTitle, Rank rankID);

    List<CriticalLearning> findByRankID(Rank rankID);
}
