package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.ClassType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassTypeRepository extends JpaRepository<ClassType, Integer> {
    Optional<ClassType> findByClassTypeName(String classType);
}
