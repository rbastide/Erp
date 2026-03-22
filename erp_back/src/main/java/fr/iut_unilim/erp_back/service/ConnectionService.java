package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.UserResponse;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConnectionService {
    private final ConnectionRepository connectionRepository;
    private final UniversityDepartmentService universityDepartmentService;

    public ConnectionService(ConnectionRepository connectionRepository, UniversityDepartmentService universityDepartmentService) {
        this.connectionRepository = connectionRepository;
        this.universityDepartmentService = universityDepartmentService;
    }

    public List<UserResponse> getAllConnectionFromDepartment(@NotNull String identifier) {
        Connection senderConnection = findByIdentifier(identifier);
        UniversityDepartment department = senderConnection.getUniversityDepartment();

        if (department == null) return new ArrayList<>();

        List<Connection> connections = connectionRepository.findAllByUniversityDepartment(department);
        return convertToUserResponse(connections);
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

    public UniversityDepartment findByDepartmentFromAuthentification(Authentication authentication) {
        Connection connection = findByIdentifier(authentication.getName());
        return connection.getUniversityDepartment();
    }

    private List<UserResponse> convertToUserResponse(List<Connection> connections) {
        List<UserResponse> userResponses = new ArrayList<>();
        for (Connection connection : connections) {
            userResponses.add(convertToUserResponse(connection));
        }
        return userResponses;
    }

    private UserResponse convertToUserResponse(Connection connection) {
        return new UserResponse(
                connection.getId(),
                connection.getIdentifier(),
                connection.getRole().getRoleName(),
                connection.getEmail(),
                connection.getLastName(),
                connection.getFirstName()
        );
    }
}
