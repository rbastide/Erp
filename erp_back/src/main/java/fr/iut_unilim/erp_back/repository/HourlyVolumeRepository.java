package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.HourlyVolume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HourlyVolumeRepository extends JpaRepository<HourlyVolume, Long> {
    List<HourlyVolume> findByHourlyVolID(Long hourlyVolumeID);
}
