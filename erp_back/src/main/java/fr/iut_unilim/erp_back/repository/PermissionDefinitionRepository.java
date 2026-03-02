package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.PermissionDefinition;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionDefinitionRepository extends JpaRepository<PermissionDefinition, Long> {
    @NotNull
    @Override
    Optional<PermissionDefinition> findById(@NotNull Long id);

    PermissionDefinition findByPermissionKey(String permissionKey);
}
