package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.ImprovementIdeas;
import fr.iut_unilim.erp_back.repository.ImprovementIdeasRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImprovementIdeasService {
    private final ImprovementIdeasRepository improvementIdeasRepository;

    public ImprovementIdeasService(ImprovementIdeasRepository improvementIdeasRepository1) {
        this.improvementIdeasRepository = improvementIdeasRepository1;
    }

    public Optional<ImprovementIdeas> findById(Long id) {
        return improvementIdeasRepository.findById(id);
    }
}
