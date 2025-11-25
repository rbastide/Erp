package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.PedagologicalTeachersFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedagologicalTeachersFeedbackRepository extends JpaRepository<PedagologicalTeachersFeedback, Long>{
    List<PedagologicalTeachersFeedback> findByPedagologicalTeachersFeedbackID (Long id);
}
