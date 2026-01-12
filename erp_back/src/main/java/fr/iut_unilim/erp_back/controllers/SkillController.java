package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.*;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.repository.*;
import fr.iut_unilim.erp_back.service.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/skill")
public class SkillController {

    private final SkillService skillService;
    private final RankService rankService;
    private final CriticalLearningRepository criticalLearningRepository;
    private final CriticalLearningService criticalLearningService;
    private final SkillRepository skillRepository;
    private final RankRepository rankRepository;

    public SkillController(SkillService skillService, RankService rankService, CriticalLearningRepository criticalLearningRepository, CriticalLearningService criticalLearningService, SkillRepository skillRepository, RankRepository rankRepository) {
        this.skillService = skillService;
        this.rankService = rankService;
        this.criticalLearningRepository = criticalLearningRepository;
        this.criticalLearningService = criticalLearningService;
        this.skillRepository = skillRepository;
        this.rankRepository = rankRepository;
    }

    @GetMapping("/skills")
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public ResponseEntity<?> getAllSkills() {
        List<Skill> skills = skillService.getAllSkills();
        return ResponseEntity.ok(convertSkillEntitiesToDtos(skills));
    }

    @PostMapping("/skills")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addSkills(@RequestBody @NotNull List<NewSkillDto> skillsDtos) {
        convertSkillDtosToEntities(skillsDtos);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/skills/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteSkill(@PathVariable Long id) {
        if (skillService.getSkillsFromId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        skillRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private void convertSkillDtosToEntities(@NotNull List<NewSkillDto> skillsDtos) {
        for (NewSkillDto skillDto : skillsDtos) {
            Skill skill = (skillDto.id() != null)
                    ? skillService.getSkillsFromId(skillDto.id()).orElse(new Skill(skillDto.skillName(), skillDto.skillNum()))
                    : new Skill(skillDto.skillName(), skillDto.skillNum());

            skill.setSkillName(skillDto.skillName());
            skill.setSkillNum(skillDto.skillNum());
            skill = skillRepository.save(skill);

            convertRankDtosToEntities(skillDto, skill);
        }
    }

    private void convertRankDtosToEntities(NewSkillDto skillDto, Skill skill) {
        for (LearningRankDto rankDto : skillDto.niveaux()) {
            Rank rank = (rankDto.id() != null)
                    ? rankService.getRankFromId(rankDto.id()).orElse(new Rank(rankDto.num(), rankDto.intitule(), skill))
                    : new Rank(rankDto.num(), rankDto.intitule(), skill);

            rank.setRankNum(rankDto.num());
            rank.setRankTitle(rankDto.intitule());
            rank.setSkillID(skill);
            rank = rankRepository.save(rank);

            convertCriticalLearningDtosToEntities(rankDto, rank);
        }
    }

    private void convertCriticalLearningDtosToEntities(LearningRankDto rankDto, Rank rank) {
        for (CriticalLearningDto acDto : rankDto.acs()) {
            CriticalLearning cl = (acDto.id() != null)
                    ? criticalLearningService.getCriticalLearningFromId(acDto.id()).orElse(new CriticalLearning(acDto.num(), acDto.intitule(), rank))
                    : new CriticalLearning(acDto.num(), acDto.intitule(), rank);

            cl.setLearningNum(acDto.num());
            cl.setLearningTitle(acDto.intitule());
            cl.setRankID(rank);
            criticalLearningRepository.save(cl);
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
            List<CriticalLearningDto> criticalLearningDtos = new ArrayList<>();
            for (CriticalLearning cl : criticalLearnings) {
                criticalLearningDtos.add(new CriticalLearningDto(cl));
            }
            learningRankDtos.add(new LearningRankDto(rank, criticalLearningDtos));
        }
        return learningRankDtos;
    }
}