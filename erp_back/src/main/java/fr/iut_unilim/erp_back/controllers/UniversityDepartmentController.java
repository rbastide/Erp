package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.UniversityDepartmentRequest;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.UniversityDepartmentRepository;
import fr.iut_unilim.erp_back.service.UniversityDepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/universityDepartment")
public class UniversityDepartmentController {

    private final UniversityDepartmentService universityDepartmentService;
    private final UniversityDepartmentRepository universityDepartmentRepository;

    public UniversityDepartmentController(UniversityDepartmentService universityDepartmentService, UniversityDepartmentRepository universityDepartmentRepository) {
        this.universityDepartmentService = universityDepartmentService;
        this.universityDepartmentRepository = universityDepartmentRepository;
    }

    @GetMapping("/getUniversityDepartments")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> getUniversityDepartments() {
        return ResponseEntity.ok(universityDepartmentRepository.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUniversityDepartment(@RequestBody UniversityDepartmentRequest dtoRequest) {

        UniversityDepartment universityDepartment = new UniversityDepartment();
        return ResponseEntity.ok(universityDepartmentRepository.save(universityDepartment));
        //TODO
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> deleteUniversityDepartment(@PathVariable Long id) {

        if (!universityDepartmentRepository.existsById(id)) {
            return ResponseEntity.ok().build();
        }
        universityDepartmentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
