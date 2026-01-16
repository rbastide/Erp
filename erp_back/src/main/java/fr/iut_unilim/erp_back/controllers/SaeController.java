package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.SaeRequest;
import fr.iut_unilim.erp_back.entity.Sae;
import fr.iut_unilim.erp_back.repository.SaeRepository;
import fr.iut_unilim.erp_back.service.SaeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/getAllSae")
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public ResponseEntity<?> getAllSae() {
        return ResponseEntity.ok(saeService.getAllSaes());
    }

    @PostMapping("/addSae")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addSae(@RequestBody List<SaeRequest> saeRequest) {

        for (SaeRequest sae : saeRequest) {
            Optional<Sae> existingSaes = saeRepository.findById(sae.getSaeID());

            if (existingSaes.isPresent()) {
                Sae saeToUpdate = existingSaes.get();
                saeToUpdate.setNum(sae.getNum());
                saeToUpdate.setTitle(sae.getTitle());
                saeRepository.save(saeToUpdate);
            } else {
                Sae newSae = new Sae();
                newSae.setNum(sae.getNum());
                newSae.setTitle(sae.getTitle());
                saeRepository.save(newSae);
            }
        }

        return ResponseEntity.ok().body("SAE bien ajoutée");
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
