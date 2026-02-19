package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.CourseHours;
import fr.iut_unilim.erp_back.repository.CourseHoursRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseHoursService {

    private final CourseHoursRepository courseHoursRepository;

    public CourseHoursService(CourseHoursRepository courseHoursRepository) {
        this.courseHoursRepository = courseHoursRepository;
    }

    public List<CourseHours> getAllCourseHoursFromDatas(Float hoursCM, Float hoursTD, Float hoursTP, Float hoursDSTP) {
        return courseHoursRepository.findByDatas(hoursCM, hoursTD, hoursTP, hoursDSTP);
    }

    public CourseHours save(CourseHours courseHours) {
        courseHoursRepository.save(courseHours);
        return courseHours;
    }

    public List<CourseHours> getAllCourseHours() {
        return courseHoursRepository.findAll();
    }

    public Optional<CourseHours> findById(Long id) {
        return courseHoursRepository.findById(id);
    }
}