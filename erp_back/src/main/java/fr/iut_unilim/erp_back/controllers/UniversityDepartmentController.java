package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.UniversityDepartmentRequest;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.UniversityDepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/universityDepartment")
public class UniversityDepartmentController {

    private final UniversityDepartmentRepository universityDepartmentRepository;

    public UniversityDepartmentController(UniversityDepartmentRepository universityDepartmentRepository) {
        this.universityDepartmentRepository = universityDepartmentRepository;
    }

    @GetMapping("/getUniversityDepartments")
    @PreAuthorize("@securityService.hasPermission('DEPARTMENT_MANAGEMENT')")
    public ResponseEntity<?> getUniversityDepartments() {
        return ResponseEntity.ok(universityDepartmentRepository.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUniversityDepartment(@RequestBody UniversityDepartmentRequest dtoRequest) {

        UniversityDepartment universityDepartment = new UniversityDepartment();
        universityDepartment.setUniversityName(dtoRequest.getUniversityName());
        universityDepartment.setUniversityDepartmentName(dtoRequest.getUniversityDepartmentName());
        universityDepartment.setCity(dtoRequest.getCity());
        universityDepartment.setCreationDate(new Date());

        return ResponseEntity.ok(universityDepartmentRepository.save(universityDepartment));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@securityService.hasPermission('DEPARTMENT_MANAGEMENT')")
    public ResponseEntity<?> deleteUniversityDepartment(@PathVariable Long id) {

        if (!universityDepartmentRepository.existsById(id)) {
            return ResponseEntity.ok().build();
        }
        universityDepartmentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
