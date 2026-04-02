package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.ErpBackApplication;
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
        Optional<Connection> connectionOptional = connectionService.findByIdentifier(username);
        if (connectionOptional.isEmpty()) return new ArrayList<>();

        Connection connection = connectionOptional.get();
        Role role = connection.getRole();

        List<MenuResponse> authorizedMenus = new ArrayList<>();
        List<AvailableMenu> menus = availableMenuRepository.findByParentIdIsNull();
        for (AvailableMenu menu : menus) {
            Optional<MenuResponse> availableMenuOptional = resolveAuthorizedMenu(menu, role);
            availableMenuOptional.ifPresent(authorizedMenus::add);
        }

        return authorizedMenus;
    }

    private Optional<MenuResponse> resolveAuthorizedMenu(AvailableMenu menu, Role role) {
        boolean isLeaf = menu.getChildren().isEmpty();
        boolean hasPermission = permissionService.hasPrivilege(role, menu.getPermissionKey());
        ErpBackApplication.LOGGER.info("Menu: " + menu.getLabel() + " has permission: " + hasPermission);

        if (isLeaf && !hasPermission) {
            return Optional.empty();
        }

        MenuResponse menuResponse = convertEntityToResponse(menu, role);

        boolean hasNoAuthorizedChildren = menuResponse.children().isEmpty();
        if (!isLeaf && hasNoAuthorizedChildren) return Optional.empty();

        return Optional.of(menuResponse);
    }

    private MenuResponse convertEntityToResponse(AvailableMenu menu, Role role) {
        List<AvailableMenu> subMenus = menu.getChildren();
        List<MenuResponse> children = subMenus.stream()
                .filter((cMenu) -> permissionService.hasPrivilege(role, cMenu.getPermissionKey()))
                .map((cMenu) -> convertEntityToResponse(cMenu, role))
                .toList();

        return new MenuResponse(menu.getId(), menu.getLabel(), menu.getRoute(), menu.getIconId(), children);
    }
}
