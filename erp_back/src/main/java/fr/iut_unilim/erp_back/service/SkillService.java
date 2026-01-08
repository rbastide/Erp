package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Skill;
import fr.iut_unilim.erp_back.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getSkillsByNum(int skillNum) {
        return skillRepository.findBySkillNum(skillNum);
    }

    public boolean doSkillNumExists(int skillNum) {
        return !getSkillsByNum(skillNum).isEmpty();
    }

    public Optional<Skill> getSkillsFromId(Long id) {
        return skillRepository.findById(id);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
}
