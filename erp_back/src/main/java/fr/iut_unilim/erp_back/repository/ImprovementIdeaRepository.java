package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.ImprovementIdea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImprovementIdeaRepository extends JpaRepository<ImprovementIdea, Integer> {
    Optional<ImprovementIdea> findByContent(String content);
}
