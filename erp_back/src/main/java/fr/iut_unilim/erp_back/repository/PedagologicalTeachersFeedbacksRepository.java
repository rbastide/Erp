package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.PedagologicalTeachersFeedbacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedagologicalTeachersFeedbacksRepository extends JpaRepository<PedagologicalTeachersFeedbacks, Long>{
    List<PedagologicalTeachersFeedbacks> findByPedagologicalTeachersFeedbackID (Long id);
}
