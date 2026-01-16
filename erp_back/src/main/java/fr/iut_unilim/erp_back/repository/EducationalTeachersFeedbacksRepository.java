package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.EducationalTeachersFeedbacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationalTeachersFeedbacksRepository extends JpaRepository<EducationalTeachersFeedbacks, Long> {
    List<EducationalTeachersFeedbacks> findByTeachersFeedbackID(Long id);
}
