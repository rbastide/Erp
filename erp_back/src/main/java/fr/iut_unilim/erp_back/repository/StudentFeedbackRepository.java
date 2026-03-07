package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.StudentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentFeedbackRepository extends JpaRepository<StudentFeedback, Integer> {
    Optional<StudentFeedback> findByContent(String content);
}
