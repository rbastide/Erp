package fr.iut_unilim.erp_back.loaders;

import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import fr.iut_unilim.erp_back.repository.UniversityDepartmentRepository;
import fr.iut_unilim.erp_back.service.ConnectionService;
import fr.iut_unilim.erp_back.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static fr.iut_unilim.erp_back.tools.utils.HashGenerator.generateBlindIndex;

@Component
@Order(3)
public class ConnectionLoader implements CommandLineRunner {
    private final ConnectionService connectionService;
    private final ConnectionRepository connectionRepository;
    private final RoleService roleService;
    private final UniversityDepartmentRepository universityDepartmentRepository;

    private static final String SUPER_ADMIN_ROLE = "Super-Admin";
    private static final String SUPER_ADMIN_IDENTIFIER = "admin";

    public ConnectionLoader(ConnectionService connectionService, ConnectionRepository connectionRepository, RoleService roleService, UniversityDepartmentRepository universityDepartmentRepository) {
        this.connectionService = connectionService;
        this.connectionRepository = connectionRepository;
        this.roleService = roleService;
        this.universityDepartmentRepository = universityDepartmentRepository;
    }

    @Override
    public void run(String... args) {
        if (connectionService.countAllConnections() != 0) return;

        Connection user = new Connection();
        user.setIdentifier(SUPER_ADMIN_IDENTIFIER);
        String hashedIdentifier;
        try {
            hashedIdentifier = generateBlindIndex(SUPER_ADMIN_IDENTIFIER);
        } catch (Exception e) {
            hashedIdentifier = null;
        }
        user.setHashedIdentifier(hashedIdentifier);

        user.setRole(roleService.createOrAccessRoleByRoleName(SUPER_ADMIN_ROLE));

        UniversityDepartment universityDepartment = universityDepartmentRepository.findById(1L).orElse(null);
        user.setUniversityDepartment(universityDepartment);

        connectionRepository.save(user);
    }
}
