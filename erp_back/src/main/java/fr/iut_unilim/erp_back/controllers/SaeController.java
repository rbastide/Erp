package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.service.SaeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sae")
public class SaeController {

    private final SaeService saeService;

    public SaeController(SaeService saeService) { this.saeService = saeService;}

    @GetMapping("/getSae")
    public ResponseEntity<?> getSae() { return ResponseEntity.ok(saeService.getAllSaes()); }
}
