package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.TeachersEducationalFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationalTeachersFeedbacksRepository extends JpaRepository<TeachersEducationalFeedback, Long> {
    List<TeachersEducationalFeedback> findByTeachersFeedbackID(Long id);
}
