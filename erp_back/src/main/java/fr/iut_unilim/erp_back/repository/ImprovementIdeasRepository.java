package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.ImprovementIdea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImprovementIdeasRepository extends JpaRepository<ImprovementIdea, Long> {
    List<ImprovementIdea> findByImprovementsIdeaID(Long improvementsIdeaID);
}
