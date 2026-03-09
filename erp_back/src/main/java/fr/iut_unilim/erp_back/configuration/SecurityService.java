package fr.iut_unilim.erp_back.configuration;

import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import fr.iut_unilim.erp_back.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityService")
public class SecurityService {

    private final PermissionService permissionService;
    private final ConnectionRepository connectionRepository;

    public SecurityService(PermissionService permissionService, ConnectionRepository connectionRepository) {
        this.permissionService = permissionService;
        this.connectionRepository = connectionRepository;
    }

    public boolean hasPermission(String permissionKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || permissionKey == null) return false;

        String username = auth.getName();
        if (username == null) return false;

        Connection connection = connectionRepository.findByIdentifier(username);

        if (connection == null) return false;

        return permissionService.hasPrivilege(connection.getRole(), permissionKey);
    }

    public boolean isLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null;
    }
}
