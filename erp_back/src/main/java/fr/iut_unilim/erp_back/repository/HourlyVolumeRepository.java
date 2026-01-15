package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.HourlyVolume;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HourlyVolumeRepository extends JpaRepository<HourlyVolume, Long> {
    List<HourlyVolume> findByHourlyVolID(Long hourlyVolumeID);

    @Query("SELECT h FROM HourlyVolume h WHERE h.nbHoursCM = :hoursCM AND h.nbHoursTD = :hoursTD AND h.nbHoursTP = :hoursTP AND h.nbHoursDSTP = :hoursDSTP")
    List<HourlyVolume> findByDatas(@Param("hoursCM") int hoursCM, @Param("hoursTD") int hoursTD, @Param("hoursTP") int hoursTP, @Param("hoursDSTP") int hoursDSTP);

    @NotNull Optional<HourlyVolume> findById(@NotNull Long hourlyVolumeId);
}