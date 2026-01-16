package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.RegisterRequest;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Teacher;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.TeacherRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ConnectionService connectionService;

    public TeacherService(TeacherRepository teacherRepository, ConnectionService connectionService) {
        this.teacherRepository = teacherRepository;
        this.connectionService = connectionService;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public List<Teacher> getAllTeachersFromDepartment(@NotNull String identifier) {
        Connection senderConnection = connectionService.findByIdentifier(identifier);
        UniversityDepartment department = senderConnection.getUniversityDepartment();

        return teacherRepository.findAllByUniversityDepartment(department);
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

    public Teacher getTeacherInfoByUser(Long userID) {
        return teacherRepository.findByUserID(userID);
    }

}