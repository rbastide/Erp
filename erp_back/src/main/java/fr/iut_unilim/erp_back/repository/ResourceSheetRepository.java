package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.ResourceSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResourceSheetRepository extends JpaRepository<ResourceSheet, Long> {
    List<ResourceSheet> findByResourceSheetId(Long resourceSheetId);
}
