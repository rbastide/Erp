package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.entity.Skill;
import fr.iut_unilim.erp_back.service.SkillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    @GetMapping
    public List<Skill> search(@RequestParam String name) {
        return skillService.getAllSkills();
    }
}
