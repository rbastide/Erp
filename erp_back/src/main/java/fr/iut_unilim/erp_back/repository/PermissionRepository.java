package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Role;
import fr.iut_unilim.erp_back.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<RolePermission, Long> {
    Optional<RolePermission> findByRole(Role role);
}
