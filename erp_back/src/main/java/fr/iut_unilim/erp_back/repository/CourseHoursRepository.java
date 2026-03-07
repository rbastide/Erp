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
    Optional<CourseHours> findByNbMinCMAndNbMinTDAndNbMinTPAndNbMinDSTPAndNbMinDS(Float nbMinCM, Float nbMinTD, Float nbMinTP, Float nbMinDSTP, Float nbMinDS);

    @NotNull Optional<CourseHours> findById(@NotNull Long courseHoursID);
}