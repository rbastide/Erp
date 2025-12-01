package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Skill;
import fr.iut_unilim.erp_back.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }
}
