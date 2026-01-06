package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Rank;
import fr.iut_unilim.erp_back.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankRepository extends JpaRepository<Rank,Long> {
    List<Rank> findByRankNum(int rankNum);
}
