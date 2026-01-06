package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Sae;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaeRepository extends JpaRepository<Sae, Long> {
    List<Sae> findByNum(String num);
}
