package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.CourseHours;
import fr.iut_unilim.erp_back.repository.CourseHoursRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseHoursService {

    private final CourseHoursRepository courseHoursRepository;

    public CourseHoursService(CourseHoursRepository courseHoursRepository) {
        this.courseHoursRepository = courseHoursRepository;
    }

    public Optional<CourseHours> findCourseHoursFromDatas(Float minCM, Float minTD, Float minTP, Float minDSTP, Float minDS) {
        return courseHoursRepository.findByNbMinCMAndNbMinTDAndNbMinTPAndNbMinDSTPAndNbMinDS(minCM, minTD, minTP, minDSTP, minDS);
    }

    public CourseHours save(CourseHours courseHours) {
        courseHoursRepository.save(courseHours);
        return courseHours;
    }

    @NotNull CourseHours findOrCreateCourseHoursFromHours(float minCM, float minTD, float minTP, float minDSTP, float minDS) {
        Optional<CourseHours> courseHours = findCourseHoursFromDatas(minCM, minTD, minTP, minDSTP, minDS);
        return courseHours.orElseGet(() -> new CourseHours(minCM, minDS, minDSTP, minTP, minTD));
    }

    public List<CourseHours> getAllCourseHours() {
        return courseHoursRepository.findAll();
    }

    public Optional<CourseHours> findById(Long id) {
        return courseHoursRepository.findById(id);
    }
}