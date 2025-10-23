package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.entity.Professeur;
import fr.iut_unilim.erp_back.service.ProfesseurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professeurs")
public class ProfesseurController {

    private final ProfesseurService professeurService;

    public ProfesseurController(ProfesseurService professeurService) {
        this.professeurService = professeurService;
    }

    @GetMapping
    public List<Professeur> getAllProfesseurs() {
        return professeurService.getAllProfesseurs();
    }

    @GetMapping("/search")
    public List<Professeur> search(@RequestParam String nom) {
        return professeurService.searchByNom(nom);
    }
}
