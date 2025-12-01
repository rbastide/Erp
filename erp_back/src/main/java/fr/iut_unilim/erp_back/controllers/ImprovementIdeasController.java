package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.service.ImprovementIdeasService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/improvementIdeas")
public class ImprovementIdeasController {

    private ImprovementIdeasService improvementIdeasService;

    public ImprovementIdeasController(ImprovementIdeasService improvementIdeasService) {
        this.improvementIdeasService = improvementIdeasService;
    }
}
