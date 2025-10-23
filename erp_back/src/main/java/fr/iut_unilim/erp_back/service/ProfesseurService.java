package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Professeur;
import fr.iut_unilim.erp_back.repository.ProfesseurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesseurService {

    private final ProfesseurRepository professeurRepository;

    public ProfesseurService(ProfesseurRepository professeurRepository) {
        this.professeurRepository = professeurRepository;
    }

    public List<Professeur> getAllProfesseurs() {
        return professeurRepository.findAll();
    }

    public List<Professeur> searchByNom(String nom) {
        return professeurRepository.findByNomContaining(nom);
    }
}
