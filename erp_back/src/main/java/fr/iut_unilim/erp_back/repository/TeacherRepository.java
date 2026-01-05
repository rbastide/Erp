package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByLastnameContaining(String lastname);
    List<Teacher> findByFirstnameContaining(String firstname);

    List<Teacher> findByFirstnameAndLastname(String firstname, String lastname);
}