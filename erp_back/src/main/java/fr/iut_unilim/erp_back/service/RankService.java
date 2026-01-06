package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.repository.RankRepository;
import org.springframework.stereotype.Service;


@Service

public class RankService {
    private final RankRepository rankRepository;

    public RankService(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }
}
