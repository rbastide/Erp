package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.PermissionDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionDefinitionRepository extends JpaRepository<PermissionDefinition, Long> {
}