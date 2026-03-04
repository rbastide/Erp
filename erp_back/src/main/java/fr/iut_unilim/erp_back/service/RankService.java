package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Rank;
import fr.iut_unilim.erp_back.entity.Skill;
import fr.iut_unilim.erp_back.repository.RankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    public List<Rank> getRanksFromSkill(Skill skill) {
        return rankRepository.findBySkillID(skill);
    }

    public List<Rank> getRanksFromNumAndTitle(int rankNum, String rankTitle) {
        return rankRepository.findByRankNumAndRankTitle(rankNum, rankTitle);
    }

    public Optional<Rank> getRankFromId(Long id) {
        return rankRepository.findById(id);
    }

    public List<Rank> getRanksByNumAndUe(int Num, int Ue) {
        return rankRepository.findByRankNumAndSkillID_SkillNum(Num, Ue);
    }
}
