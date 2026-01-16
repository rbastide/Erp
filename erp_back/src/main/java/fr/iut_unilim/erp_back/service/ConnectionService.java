package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.UserResponse;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import fr.iut_unilim.erp_back.repository.TeacherRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConnectionService {
    private final ConnectionRepository connectionRepository;
    private final TeacherRepository teacherRepository;
    private final UniversityDepartmentService universityDepartmentService;

    public ConnectionService(ConnectionRepository connectionRepository, TeacherRepository teacherRepository, UniversityDepartmentService universityDepartmentService) {
        this.connectionRepository = connectionRepository;
        this.teacherRepository = teacherRepository;
        this.universityDepartmentService = universityDepartmentService;
    }

    public List<UserResponse> getAllConnections() {
        List<Connection> connections = connectionRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();

        for (Connection connection : connections) {

            String lastname = teacherRepository.findLastnameByTeacherID(connection.getId());
            String firstname = teacherRepository.findFirstnameByTeacherID(connection.getId());

            if (lastname == null) lastname = "";
            if (firstname == null) firstname = "";

            userResponses.add(new UserResponse(
                    connection.getId(),
                    connection.getIdentifier(),
                    connection.getRole(),
                    connection.getEmail(),

                    lastname,
                    firstname
            ));
        }
        return userResponses;
    }

    public Connection findByIdentifier(@NotNull String identifier) {
        return connectionRepository.findByIdentifier(identifier);
    }

    public boolean updateDepartment(@NotNull String identifier, @NotNull Long departmentId) {
        Connection connection = findByIdentifier(identifier);
        Optional<UniversityDepartment> universityDepartment = universityDepartmentService.getUniversityDepartmentById(departmentId);
        if (universityDepartment.isEmpty()) return false;
        connection.setUniversityDepartment(universityDepartment.get());
        connectionRepository.save(connection);
        return true;
    }
}
