package fr.iut_unilim.erp_back.configuration;

import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.service.ConnectionService;
import fr.iut_unilim.erp_back.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("securityService")
public class SecurityService {

    private final PermissionService permissionService;
    private final ConnectionService connectionService;

    public SecurityService(PermissionService permissionService, ConnectionService connectionService) {
        this.permissionService = permissionService;
        this.connectionService = connectionService;
    }

    public boolean hasPermission(String permissionKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || permissionKey == null) return false;

        String username = auth.getName();
        if (username == null) return false;

        Optional<Connection> connection = connectionService.findByIdentifier(username);

        return connection
                .filter(value -> permissionService.hasPrivilege(value.getRole(), permissionKey))
                .isPresent();

    }

    public boolean isLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null;
    }
}
