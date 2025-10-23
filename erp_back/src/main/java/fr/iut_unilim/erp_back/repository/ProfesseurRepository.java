package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {
    List<Professeur> findByNomContaining(String nom);
}
