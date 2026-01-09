package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.AuthResponse;
import fr.iut_unilim.erp_back.dto.TeacherRequest;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Teacher;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import fr.iut_unilim.erp_back.repository.TeacherRepository;
import fr.iut_unilim.erp_back.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/search")
    public List<Teacher> search(@RequestParam String name) {
        return teacherService.findByLastname(name);
    }




}
