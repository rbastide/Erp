package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.RegisterRequest;
import fr.iut_unilim.erp_back.dto.TeacherRequest;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Teacher;
import fr.iut_unilim.erp_back.repository.TeacherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public List<Teacher> findByLastname(String lastname) {
        return teacherRepository.findByLastnameContaining(lastname);
    }

    public List<Teacher> findByFirstname(String firstname) {
        return teacherRepository.findByFirstnameContaining(firstname);
    }

    public List<Teacher> findByFirstnameAndLastname(String lastname, String firstname) {
        return teacherRepository.findByFirstnameAndLastname(lastname, firstname);
    }

    public void addTeacher(@RequestBody TeacherRequest teacherRequest, Long userID) {
        if(userID == null) {
            ResponseEntity.notFound().build();
            return;
        }
        Optional<Teacher> existingTeacher = teacherRepository.findById(teacherRequest.getTeacherID());
        if(existingTeacher.isEmpty()){
            Teacher teacher = new Teacher();
            teacher.setLastname(teacherRequest.getLastName());
            teacher.setFirstname(teacherRequest.getFirstName());
            teacher.setuserID(userID);
            teacherRepository.save(teacher);
        }
        Teacher teacherToEdit = existingTeacher.get();
        teacherToEdit.setLastname(teacherRequest.getLastName());
        teacherToEdit.setFirstname(teacherRequest.getFirstName());
        teacherToEdit.setuserID(userID);
        teacherRepository.save(teacherToEdit);

        ResponseEntity.ok().build();

    }

    public void createTeacherFromRegister(RegisterRequest req, Connection connection) {
        Teacher teacher = new Teacher();

        teacher.setFirstname(req.getFirstname());
        teacher.setLastname(req.getLastname());
        teacher.setuserID(connection.getId());
        teacherRepository.save(teacher);
    }

    public void save(Teacher teacher) {
        teacherRepository.save(teacher);
    }

}