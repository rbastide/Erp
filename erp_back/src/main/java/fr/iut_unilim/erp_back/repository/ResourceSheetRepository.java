package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.entity.ResourceSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceSheetRepository extends JpaRepository<ResourceSheet, Long> {
    List<ResourceSheet> findAllByResourceAndAcademicYearStart(Resource resource, Integer academicYearStart);
    Optional<ResourceSheet> findByResourceAndAcademicYearStart(Resource resource, Integer academicYearStart);

    Collection<? extends ResourceSheet> findAllByResource(Resource resource);
}
