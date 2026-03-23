package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.TeacherResponse;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.service.ConnectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    private final ConnectionService connectionService;

    private static final String[] TEACHERS_ROLE_NAMES = {"Professeur", "Vacataire"};

    public TeacherController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PreAuthorize("@securityService.hasPermission('RESOURCE_SHEET_MANAGEMENT')")
    @GetMapping("")
    public ResponseEntity<?> getTeachers(Authentication authentication) {
        UniversityDepartment universityDepartment = connectionService.findByDepartmentFromAuthentification(authentication);

        try {
            List<Connection> teachers = connectionService.getAllConnectionsFromDepartmentAndRoleNames(universityDepartment, TEACHERS_ROLE_NAMES);
            List<TeacherResponse> teacherResponses = connectionService.convertEntityToTeacherResponse(teachers);
            return ResponseEntity.ok(teacherResponses);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
