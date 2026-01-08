package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.CriticalLearningDto;
import fr.iut_unilim.erp_back.dto.LearningRankDto;
import fr.iut_unilim.erp_back.dto.NewSkillDto;
import fr.iut_unilim.erp_back.entity.CriticalLearning;
import fr.iut_unilim.erp_back.entity.Rank;
import fr.iut_unilim.erp_back.entity.Skill;
import fr.iut_unilim.erp_back.repository.CriticalLearningRepository;
import fr.iut_unilim.erp_back.repository.SkillRepository;
import fr.iut_unilim.erp_back.service.CriticalLearningService;
import fr.iut_unilim.erp_back.service.RankService;
import fr.iut_unilim.erp_back.service.SkillService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;
    private final RankService rankService;
    private final CriticalLearningRepository criticalLearningRepository;
    private final CriticalLearningService criticalLearningService;
    private final SkillRepository skillRepository;

    public SkillController(SkillService skillService, RankService rankService, CriticalLearningRepository criticalLearningRepository, CriticalLearningService criticalLearningService, SkillRepository skillRepository) {
        this.skillService = skillService;
        this.rankService = rankService;
        this.criticalLearningRepository = criticalLearningRepository;
        this.criticalLearningService = criticalLearningService;
        this.skillRepository = skillRepository;
    }

    @GetMapping("/skills")
    public ResponseEntity<?> getAllSkills() {
        List<Skill> skills = skillService.getAllSkills();
        List<NewSkillDto> skillsDtos = convertSkillEntitiesToDtos(skills);

        return ResponseEntity.ok(skillsDtos);
    }

    @NotNull
    private static List<CriticalLearningDto> convertCriticalLearningEntitiesToDtos(@NotNull List<CriticalLearning> criticalLearnings) {
        List<CriticalLearningDto> criticalLearningDtos = new ArrayList<>();
        for (CriticalLearning criticalLearning : criticalLearnings) {
            criticalLearningDtos.add(new CriticalLearningDto(criticalLearning));
        }
        return criticalLearningDtos;
    }

    @PostMapping("/skillsd")
    public ResponseEntity<?> addSkills(@RequestBody @NotNull List<NewSkillDto> skillsDtos) {
        convertSkillDtosToEntities(skillsDtos);

        return ResponseEntity.ok().build();
    }

    private void convertSkillDtosToEntities(@NotNull List<NewSkillDto> skillsDtos) {
        for (NewSkillDto skillDto : skillsDtos) {
            Optional<Skill> skillDbRequest = skillService.getSkillsFromId(skillDto.id());

            Skill skill = skillDbRequest.orElse(new Skill(skillDto.skillName(), skillDto.skillNum()));
            skill.setSkillName(skillDto.skillName());
            skill.setSkillNum(skillDto.skillNum());

            convertRankDtosToEntities(skillDto, skill);
            skillRepository.save(skill);
        }
    }

    private void convertRankDtosToEntities(NewSkillDto skillDto, Skill skill) {
        for (LearningRankDto rankDto : skillDto.niveaux()) {
            Optional<Rank> rankDbRequest = rankService.getRankFromId(rankDto.id());

            Rank rank = rankDbRequest.orElse(new Rank(rankDto.num(), rankDto.intitule(), skill));
            rank.setRankNum(rankDto.num());
            rank.setRankTitle(rankDto.intitule());
            rank.setSkillID(skill);

            convertCriticalLearningDtosToEntities(rankDto, rank);
        }
    }

    private void convertCriticalLearningDtosToEntities(LearningRankDto rankDto, Rank rank) {
        for (CriticalLearningDto criticalLearningDto : rankDto.acs()) {
            Optional<CriticalLearning> criticalLearningDbRequest = criticalLearningService.getCriticalLearningFromId(criticalLearningDto.id());

            CriticalLearning criticalLearning = criticalLearningDbRequest.orElse(new CriticalLearning(criticalLearningDto.num(), criticalLearningDto.intitule(), rank));
            criticalLearning.setLearningNum(criticalLearningDto.num());
            criticalLearning.setLearningTitle(criticalLearningDto.intitule());
            criticalLearning.setRankID(rank);
        }
    }

    @NotNull
    private List<NewSkillDto> convertSkillEntitiesToDtos(@NotNull List<Skill> skills) {
        List<NewSkillDto> skillsDtos = new ArrayList<>();
        for (Skill skill : skills) {
            List<Rank> ranks = rankService.getRanksFromSkill(skill);
            List<LearningRankDto> learningRankDtos = convertLearningRanksEntitiesToDtos(ranks);
            skillsDtos.add(new NewSkillDto(skill, learningRankDtos));
        }
        return skillsDtos;
    }

    @NotNull
    private List<LearningRankDto> convertLearningRanksEntitiesToDtos(@NotNull List<Rank> ranks) {
        List<LearningRankDto> learningRankDtos = new ArrayList<>();
        for (Rank rank : ranks) {
            List<CriticalLearning> criticalLearnings = criticalLearningRepository.findByRankID(rank);
            List<CriticalLearningDto> criticalLearningDtos = convertCriticalLearningEntitiesToDtos(criticalLearnings);
            learningRankDtos.add(new LearningRankDto(rank, criticalLearningDtos));
        }
        return learningRankDtos;
    }
}
