package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.ImprovementIdeas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ImprovementIdeasRepository extends JpaRepository<ImprovementIdeas,Long>{
    List<ImprovementIdeas> findByImprovementIdeaID(Long id);
}
