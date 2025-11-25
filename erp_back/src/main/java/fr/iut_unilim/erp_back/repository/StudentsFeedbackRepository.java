package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.StudentsFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsFeedbackRepository extends JpaRepository<StudentsFeedback, Long>{
    List<StudentsFeedback> findByPedagologicalTeachersFeedbackID (Long id);
}