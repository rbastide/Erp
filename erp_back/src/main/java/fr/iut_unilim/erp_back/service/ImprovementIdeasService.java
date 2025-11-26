package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.repository.HourlyVolumeRepository;
import fr.iut_unilim.erp_back.repository.ImprovementIdeasRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImprovementIdeasService {

    private final ImprovementIdeasRepository improvementIdeasRepository;

    public ImprovementIdeasService(ImprovementIdeasRepository improvementIdeasRepository) {
        this.improvementIdeasRepository = improvementIdeasRepository;
    }
}
