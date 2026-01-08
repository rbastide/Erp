package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.CriticalLearningDtos;
import fr.iut_unilim.erp_back.dto.LearningRankDtos;
import fr.iut_unilim.erp_back.dto.NewSkillDto;
import fr.iut_unilim.erp_back.entity.CriticalLearning;
import fr.iut_unilim.erp_back.entity.Rank;
import fr.iut_unilim.erp_back.entity.Skill;
import fr.iut_unilim.erp_back.repository.CriticalLearningRepository;
import fr.iut_unilim.erp_back.service.RankService;
import fr.iut_unilim.erp_back.service.SkillService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SkillController {

    private final SkillService skillService;
    private final RankService rankService;
    private final CriticalLearningRepository criticalLearningRepository;

    public SkillController(SkillService skillService, RankService rankService, CriticalLearningRepository criticalLearningRepository) {
        this.skillService = skillService;
        this.rankService = rankService;
        this.criticalLearningRepository = criticalLearningRepository;
    }

    @NotNull
    private static List<CriticalLearningDtos> convertCriticalLearningEntitiesToDtos(@NotNull List<CriticalLearning> criticalLearnings) {
        List<CriticalLearningDtos> criticalLearningDtos = new ArrayList<>();
        for (CriticalLearning criticalLearning : criticalLearnings) {
            criticalLearningDtos.add(new CriticalLearningDtos(criticalLearning));
        }
        return criticalLearningDtos;
    }

    @GetMapping("/skills")
    public ResponseEntity<?> getAllSkills() {
        List<Skill> skills = skillService.getAllSkills();
        List<NewSkillDto> skillsDtos = convertSkillEntitiesToDtos(skills);

        return ResponseEntity.ok(skillsDtos);
    }

    @NotNull
    private List<NewSkillDto> convertSkillEntitiesToDtos(@NotNull List<Skill> skills) {
        List<NewSkillDto> skillsDtos = new ArrayList<>();
        for (Skill skill : skills) {
            List<Rank> ranks = rankService.getRanksFromSkill(skill);
            List<LearningRankDtos> learningRankDtos = convertLearningRanksEntitiesToDtos(ranks);
            skillsDtos.add(new NewSkillDto(skill, learningRankDtos));
        }
        return skillsDtos;
    }

    @NotNull
    private List<LearningRankDtos> convertLearningRanksEntitiesToDtos(@NotNull List<Rank> ranks) {
        List<LearningRankDtos> learningRankDtos = new ArrayList<>();
        for (Rank rank : ranks) {
            List<CriticalLearning> criticalLearnings = criticalLearningRepository.findByRankID(rank);
            List<CriticalLearningDtos> criticalLearningDtos = convertCriticalLearningEntitiesToDtos(criticalLearnings);
            learningRankDtos.add(new LearningRankDtos(rank, criticalLearningDtos));
        }
        return learningRankDtos;
    }
}
