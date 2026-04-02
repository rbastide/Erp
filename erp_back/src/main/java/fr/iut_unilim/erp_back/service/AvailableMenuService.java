package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.MenuResponse;
import fr.iut_unilim.erp_back.entity.AvailableMenu;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Role;
import fr.iut_unilim.erp_back.repository.AvailableMenuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AvailableMenuService {
    private final PermissionService permissionService;
    private final ConnectionService connectionService;
    private final AvailableMenuRepository availableMenuRepository;

    public AvailableMenuService(PermissionService permissionService, ConnectionService connectionService, AvailableMenuRepository availableMenuRepository) {
        this.permissionService = permissionService;
        this.connectionService = connectionService;
        this.availableMenuRepository = availableMenuRepository;
    }

    public List<MenuResponse> getAvailableMenusFromUser(String username) {
        Connection connection = connectionService.findByIdentifier(username).orElse(null);
        if (connection == null) return new ArrayList<>();

        Role role = connection.getRole();
        List<AvailableMenu> roots = availableMenuRepository.findByParentIdIsNull();

        return roots.stream()
                .map(menu -> resolve(menu, role))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<MenuResponse> resolve(AvailableMenu menu, Role role) {
        List<MenuResponse> authorizedChildren = menu.getChildren().stream()
                .map(child -> resolve(child, role))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        boolean hasDirectPermission = permissionService.hasPrivilege(role, menu.getPermissionKey());
        boolean hasVisibleChildren = !authorizedChildren.isEmpty();

        if (hasDirectPermission || hasVisibleChildren) {
            return Optional.of(new MenuResponse(
                    menu.getId(),
                    menu.getLabel(),
                    menu.getRoute(),
                    menu.getIconId(),
                    authorizedChildren
            ));
        }

        return Optional.empty();
    }
}
