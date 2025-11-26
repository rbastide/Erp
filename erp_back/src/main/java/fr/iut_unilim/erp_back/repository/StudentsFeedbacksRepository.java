package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.StudentsFeedbacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsFeedbacksRepository extends JpaRepository<StudentsFeedbacks, Long>{
    List<StudentsFeedbacks> findByPedagologicalTeachersFeedbackID (Long id);
}