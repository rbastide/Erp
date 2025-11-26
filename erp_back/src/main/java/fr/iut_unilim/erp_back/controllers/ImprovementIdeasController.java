package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.entity.ImprovementIdeas;
import fr.iut_unilim.erp_back.service.ImprovementIdeasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/improvementIdeas")
public class ImprovementIdeasController {

    private ImprovementIdeasService improvementIdeasService;

    public ImprovementIdeasController(ImprovementIdeasService improvementIdeasService) {
        this.improvementIdeasService = improvementIdeasService;
    }
}
