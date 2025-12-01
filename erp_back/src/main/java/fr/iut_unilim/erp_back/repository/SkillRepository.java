package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>{
    List<Skill> findBySkillName(String skillName);
}
