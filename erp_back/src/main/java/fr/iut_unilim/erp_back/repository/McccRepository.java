package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Mccc;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.model.McccId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface McccRepository extends JpaRepository<Mccc, McccId> {
    List<Mccc> findByResourceId(Resource resourceId);

    Optional<Mccc> findByMcccId(Long mcccId);

    Optional<Mccc> findByResourceIdAndAcademicYearStart(Resource resourceId, Integer academicYearStart);

    @Query("SELECT m FROM Mccc m JOIN Resource r ON m.resourceId = r WHERE m.academicYearStart = :academicYearStart AND r.universityDepartment = :universityDepartment")
    List<Mccc> findByAcademicYearStartAndUniversityDepartment(UniversityDepartment universityDepartment, Integer academicYearStart);
}
