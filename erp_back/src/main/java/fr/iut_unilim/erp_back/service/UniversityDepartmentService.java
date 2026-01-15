package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.UniversityDepartmentRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityDepartmentService {

    private final UniversityDepartmentRepository universityDepartmentRepository;

    public UniversityDepartmentService(UniversityDepartmentRepository universityRepository) {
        this.universityDepartmentRepository = universityRepository;
    }

    public List<UniversityDepartment> getAllUniversityDepartments() {
        return universityDepartmentRepository.findAll();
    }

    public UniversityDepartment save(UniversityDepartment universityDepartment) {
        return universityDepartmentRepository.save(universityDepartment);
    }

    public void deleteUniversityDepartmentById(Long id) {
        if (!universityDepartmentRepository.existsById(id)) {
            return;
        }
        universityDepartmentRepository.deleteById(id);
    }

    public Optional<UniversityDepartment> getUniversityDepartmentById(@NotNull Long id) {
        return universityDepartmentRepository.findById(id);
    }
}
