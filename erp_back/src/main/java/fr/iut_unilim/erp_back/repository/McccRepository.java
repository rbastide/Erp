package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Mccc;
import fr.iut_unilim.erp_back.tools.datastructures.McccId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface McccRepository extends JpaRepository<Mccc, Long> {
    Mccc findByMcccId(McccId mcccId);
}
