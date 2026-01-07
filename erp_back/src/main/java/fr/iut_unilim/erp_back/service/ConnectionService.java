package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.UserResponse;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionService {
    private final ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public List<UserResponse> getAllConnections() {
        List<Connection> connections = connectionRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (Connection connection : connections) {
            UserResponse userResponse = new UserResponse(connection.getId(), connection.getIdentifier(), connection.getRole());
            userResponses.add(userResponse);
        }
        return userResponses;
    }
}
