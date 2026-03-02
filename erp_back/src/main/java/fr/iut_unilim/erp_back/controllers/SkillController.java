package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.CriticalConceptDto;
import fr.iut_unilim.erp_back.dto.LearningRankDto;
import fr.iut_unilim.erp_back.dto.NewSkillDto;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.CriticalConcept;
import fr.iut_unilim.erp_back.entity.Rank;
import fr.iut_unilim.erp_back.entity.Skill;
import fr.iut_unilim.erp_back.repository.CriticalConceptRepository;
import fr.iut_unilim.erp_back.repository.RankRepository;
import fr.iut_unilim.erp_back.repository.SkillRepository;
import fr.iut_unilim.erp_back.service.ConnectionService;
import fr.iut_unilim.erp_back.service.CriticalConceptService;
import fr.iut_unilim.erp_back.service.RankService;
import fr.iut_unilim.erp_back.service.SkillService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/skill")
public class SkillController {

    private final SkillService skillService;
    private final RankService rankService;
    private final CriticalConceptRepository criticalConceptRepository;
    private final CriticalConceptService criticalConceptService;
    private final SkillRepository skillRepository;
    private final RankRepository rankRepository;
    private final ConnectionService connectionService;

    public SkillController(SkillService skillService, RankService rankService, CriticalConceptRepository criticalConceptRepository, CriticalConceptService criticalConceptService, SkillRepository skillRepository, RankRepository rankRepository, ConnectionService connectionService) {
        this.skillService = skillService;
        this.rankService = rankService;
        this.criticalConceptRepository = criticalConceptRepository;
        this.criticalConceptService = criticalConceptService;
        this.skillRepository = skillRepository;
        this.rankRepository = rankRepository;
        this.connectionService = connectionService;
    }

    @GetMapping("/skills")
    @PreAuthorize("@securityService.hasPermission('SKILL_MANAGEMENT')")
    public ResponseEntity<?> getAllSkills(Authentication authentication) {
        List<Skill> skills = skillService.getAllSkillsFromDepartment(authentication.getName());
        return ResponseEntity.ok(convertSkillEntitiesToDtos(skills));
    }

    @PostMapping("/skills")
    @PreAuthorize("@securityService.hasPermission('SKILL_MANAGEMENT')")
    public ResponseEntity<?> addSkills(@RequestBody @NotNull List<NewSkillDto> skillsDtos, Authentication authentication) {
        convertSkillDtosToEntities(skillsDtos, authentication);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/skills/{id}")
    @PreAuthorize("@securityService.hasPermission('SKILL_MANAGEMENT')")
    public ResponseEntity<?> deleteSkill(@PathVariable Long id) {
        if (skillService.getSkillsFromId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Rank> ranks = rankService.getRanksFromSkill(skillService.getSkillsFromId(id).get());
        for (Rank rank : ranks) {
            List<CriticalConcept> criticalConcepts = criticalConceptRepository.findByRankID(rank);
            criticalConceptRepository.deleteAll(criticalConcepts);
        }
        rankRepository.deleteAll(ranks);
        skillRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private void convertSkillDtosToEntities(@NotNull List<NewSkillDto> skillsDtos, Authentication authentication) {
        for (NewSkillDto skillDto : skillsDtos) {
            Skill defaultSkill = new Skill(skillDto.skillName(), skillDto.skillNum());
            handleDepartment(defaultSkill, authentication);
            Skill skill = (skillDto.id() != null) ? skillService.getSkillsFromId(skillDto.id()).orElse(defaultSkill) : defaultSkill;

            skill.setSkillName(skillDto.skillName());
            skill.setSkillNum(skillDto.skillNum());
            skill = skillRepository.save(skill);

            convertRankDtosToEntities(skillDto, skill);
        }
    }

    private void convertRankDtosToEntities(NewSkillDto skillDto, Skill skill) {
        for (LearningRankDto rankDto : skillDto.levels()) {
            Rank rank = (rankDto.id() != null)
                    ? rankService.getRankFromId(rankDto.id()).orElse(new Rank(rankDto.num(), rankDto.title(), skill))
                    : new Rank(rankDto.num(), rankDto.title(), skill);

            rank.setRankNum(rankDto.num());
            rank.setRankTitle(rankDto.title());
            rank.setSkillID(skill);
            rank = rankRepository.save(rank);

            convertCriticalConceptDtosToEntities(rankDto, rank);
        }
    }

    private void convertCriticalConceptDtosToEntities(LearningRankDto rankDto, Rank rank) {
        for (CriticalConceptDto acDto : rankDto.acs()) {
            CriticalConcept cl = (acDto.id() != null)
                    ? criticalConceptService.getCriticalConceptFromId(acDto.id()).orElse(new CriticalConcept(acDto.num(), acDto.title(), rank))
                    : new CriticalConcept(acDto.num(), acDto.title(), rank);

            cl.setCriticalConceptNum(acDto.num());
            cl.setCriticalConceptTitle(acDto.title());
            cl.setRankID(rank);
            criticalConceptRepository.save(cl);
        }
    }

    private void handleDepartment(Skill resourceSheet, Authentication authentication) {
        Connection connection = connectionService.findByIdentifier(authentication.getName());

        if (connection == null) return;

        resourceSheet.setUniversityDepartment(connection.getUniversityDepartment());
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
            List<CriticalConcept> criticalConcepts = criticalConceptRepository.findByRankID(rank);
            List<CriticalConceptDto> criticalConceptDtos = new ArrayList<>();
            for (CriticalConcept cl : criticalConcepts) {
                criticalConceptDtos.add(new CriticalConceptDto(cl));
            }
            learningRankDtos.add(new LearningRankDto(rank, criticalConceptDtos));
        }
        return learningRankDtos;
    }
}