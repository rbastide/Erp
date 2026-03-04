package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.AvailableMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableMenuRepository extends JpaRepository<AvailableMenu, Long> {

}
