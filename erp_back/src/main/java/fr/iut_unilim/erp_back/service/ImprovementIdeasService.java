package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.repository.ImprovementIdeasRepository;
import org.springframework.stereotype.Service;

@Service
public class ImprovementIdeasService {

    private final ImprovementIdeasRepository improvementIdeasRepository;

    public ImprovementIdeasService(ImprovementIdeasRepository improvementIdeasRepository) {
        this.improvementIdeasRepository = improvementIdeasRepository;
    }
}
