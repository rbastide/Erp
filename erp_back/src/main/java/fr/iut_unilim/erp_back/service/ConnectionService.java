package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.TeacherRequest;
import fr.iut_unilim.erp_back.dto.UserResponse;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Teacher;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import fr.iut_unilim.erp_back.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionService {
    private final ConnectionRepository connectionRepository;
    private final TeacherRepository teacherRepository;

    public ConnectionService(ConnectionRepository connectionRepository, TeacherRepository teacherRepository) {
        this.connectionRepository = connectionRepository;
        this.teacherRepository = teacherRepository;
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
}
