package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.ImprovementIdeas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImprovementIdeasRepository extends JpaRepository<ImprovementIdeas, Long> {
    List<ImprovementIdeas> findByImprovementsIdeaID(Long improvementsIdeaID);
}
