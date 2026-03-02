package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.TeacherRequest;
import fr.iut_unilim.erp_back.entity.Teacher;
import fr.iut_unilim.erp_back.service.TeacherService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    @PreAuthorize("@securityService.hasPermission('USER_MANAGEMENT')")
    public List<TeacherRequest> getAllTeachers(Authentication authentication) {
        List<Teacher> teachers = teacherService.getAllTeachersFromDepartment(authentication.getName());
        return convertTeachersEntityToDto(teachers);
    }


    private List<TeacherRequest> convertTeachersEntityToDto(List<Teacher> teachers) {
        List<TeacherRequest> teacherRequests =new ArrayList<>();

        for(Teacher teacher : teachers){
            TeacherRequest teacherDto = new TeacherRequest();

            teacherDto.setTeacherID(teacher.getTeacherID());
            teacherDto.setFirstName(teacher.getFirstname());
            teacherDto.setLastName(teacher.getLastname());
            teacherDto.setUserID(teacher.getuserID());

            teacherRequests.add(teacherDto);
        }

        return teacherRequests;

    }

}
