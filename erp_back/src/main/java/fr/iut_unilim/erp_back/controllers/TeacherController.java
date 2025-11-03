package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.entity.Teacher;
import fr.iut_unilim.erp_back.service.TeacherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllProfesseurs();
    }

    @GetMapping("/search")
    public List<Teacher> search(@RequestParam String name) {
        return teacherService.searchByFirstName(name);
    }
}
