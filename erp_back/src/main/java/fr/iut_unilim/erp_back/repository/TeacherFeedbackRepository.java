package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.TeacherFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherFeedbackRepository extends JpaRepository<TeacherFeedback, Integer> {
    Optional<TeacherFeedback> findByContent(String content);
}
