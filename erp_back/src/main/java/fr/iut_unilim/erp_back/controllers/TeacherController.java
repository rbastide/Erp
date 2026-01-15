package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.entity.Teacher;
import fr.iut_unilim.erp_back.service.TeacherService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public List<Teacher> search(@RequestParam String name) {
        return teacherService.findByLastname(name);
    }




}
