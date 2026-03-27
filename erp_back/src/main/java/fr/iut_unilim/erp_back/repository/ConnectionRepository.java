package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Role;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    Connection findByIdentifier(String identifier);

    @NotNull Optional<Connection> findById(@NotNull Long id);

    @Query("SELECT email FROM Connection")
    List<String> findAllEmails();

    List<Connection> findAllByUniversityDepartment(UniversityDepartment universityDepartment);

    List<Connection> findAllByUniversityDepartmentAndRole(UniversityDepartment universityDepartment, Role role);

    List<Connection> findAllByRole(Role role);
}
