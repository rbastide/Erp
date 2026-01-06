package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Rank;
import fr.iut_unilim.erp_back.repository.RankRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class RankService {
    private final RankRepository rankRepository;

    public RankService(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    public List<Rank> getRanksByNum(int skillNum) {
        return rankRepository.findByRankNum(skillNum);
    }

    public boolean doRankNumExists(int skillNum) {
        return !getRanksByNum(skillNum).isEmpty();
    }
}
