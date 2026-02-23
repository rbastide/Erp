package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.SaeRequest;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Sae;
import fr.iut_unilim.erp_back.repository.SaeRepository;
import fr.iut_unilim.erp_back.service.ConnectionService;
import fr.iut_unilim.erp_back.service.SaeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/sae")
public class SaeController {

    private final SaeService saeService;
    private final SaeRepository saeRepository;

    public SaeController(SaeService saeService, SaeRepository saeRepository) {
        this.saeService = saeService;
        this.saeRepository = saeRepository;
    }

    @GetMapping("/saes")
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public ResponseEntity<?> getAllSae(Authentication authentication) {
        return ResponseEntity.ok(saeService.getAllSaesFromDepartment(authentication.getName()));
    }

    @PostMapping("/addSae")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addSae(@RequestBody SaeRequest saeRequest, Authentication authentication) {
        boolean hasBeenSaved = saeService.saveFromDto(saeRequest, authentication);

        if (!hasBeenSaved) {
            return ResponseEntity.badRequest().body("Une erreur est survenue lors de l'ajout de la SAE");
        }

        return ResponseEntity.ok().body("SAE bien ajoutée.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteSae(@PathVariable Long id) {
        if (!saeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        saeService.deleteSaeById(id);
        return ResponseEntity.ok().body("La SAE a bien été supprimée");
    }
}
