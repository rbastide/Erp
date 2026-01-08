package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.service.CriticalLearningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ac")
public class CriticalLearningController {

    private final CriticalLearningService criticalLearningService;

    public CriticalLearningController(CriticalLearningService criticalLearningService) {
        this.criticalLearningService = criticalLearningService;
    }

    @GetMapping("/acs")
    public ResponseEntity<?> getAcs() {
        return ResponseEntity.ok(criticalLearningService.getAllCriticalLearning());
    }
}
