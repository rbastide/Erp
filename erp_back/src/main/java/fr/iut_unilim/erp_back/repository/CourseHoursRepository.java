package fr.iut_unilim.erp_back.repository;

import fr.iut_unilim.erp_back.entity.CourseHours;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseHoursRepository extends JpaRepository<CourseHours, Long> {
    @Query("SELECT h FROM CourseHours h WHERE h.nbHoursCM = :hoursCM AND h.nbHoursTD = :hoursTD AND h.nbHoursTP = :hoursTP AND h.nbHoursDSTP = :hoursDSTP")
    List<CourseHours> findByDatas(@Param("hoursCM") Float hoursCM, @Param("hoursTD") Float hoursTD, @Param("hoursTP") Float hoursTP, @Param("hoursDSTP") Float hoursDSTP);

    @NotNull Optional<CourseHours> findById(@NotNull Long courseHoursID);
}