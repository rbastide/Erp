package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Rank;
import fr.iut_unilim.erp_back.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RankRepository extends JpaRepository<Rank,Long> {
    List<Rank> findByRankNum(int rankNum);

    List<Rank> findBySkillID(Skill skill);

    List<Rank> findByRankNumAndRankTitle(int rankNum, String rankTitle);

    @Query("SELECT r FROM Rank r JOIN r.skillID s WHERE r.rankNum = :rankNum AND s.skillNum = :skillNum")
    List<Rank> findByRankNumAndSkillID_SkillNum(@Param("rankNum") int rankNum, @Param("skillNum") int skillNum);
}
