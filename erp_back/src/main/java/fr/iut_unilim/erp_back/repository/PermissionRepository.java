package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Permission;
import fr.iut_unilim.erp_back.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByRole(Role role);
}
