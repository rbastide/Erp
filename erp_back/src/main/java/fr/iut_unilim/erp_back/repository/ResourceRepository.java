package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByName(String name);

    List<Resource> findByNum(String num);


}
