package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Mccc;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.model.McccId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface McccRepository extends JpaRepository<Mccc, McccId> {
    List<Mccc> findByResourceId(Resource resourceId);

    Optional<Mccc> findByMcccId(Long mcccId);

    Optional<Mccc> findFirstByResourceIdOrderByLastModificationDateDesc(Resource resourceId);

    List<Mccc> findAllByUniversityDepartment(UniversityDepartment universityDepartment);

    List<Mccc> findAllByUniversityDepartmentAndAcademicYearStart(UniversityDepartment universityDepartment, Integer academicYearStart);
}
