package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Connection;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    Connection findByIdentifier(String identifier);

    @NotNull Optional<Connection> findById(@NotNull Long id);
}
