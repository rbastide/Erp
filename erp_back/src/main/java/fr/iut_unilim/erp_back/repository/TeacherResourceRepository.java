package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.entity.TeacherResource;
import fr.iut_unilim.erp_back.model.TeacherResourceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherResourceRepository extends JpaRepository<TeacherResource, TeacherResourceId> {
    Optional<TeacherResource> findByConnectionAndResource(Connection connection, Resource resource);
}
