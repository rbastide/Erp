package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.Skill;

import java.util.List;

public record NewSkillDto(Long id, int skillNum, String skillName, List<LearningRankDtos> niveaux) {
    public NewSkillDto(Skill skill, List<LearningRankDtos> niveaux) {
        this(skill.getSkillID(), skill.getSkillNum(), skill.getSkillName(), niveaux);
    }
}
