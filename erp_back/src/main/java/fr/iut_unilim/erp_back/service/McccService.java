package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.repository.McccRepository;
import org.springframework.stereotype.Service;

@Service
public class McccService {
    private final McccRepository mcccRepository;

    public McccService(McccRepository mcccRepository) {
        this.mcccRepository = mcccRepository;
    }
}