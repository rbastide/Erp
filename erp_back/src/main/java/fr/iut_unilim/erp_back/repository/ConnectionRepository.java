package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    Connection findByIdentifier(String identifier);
}
