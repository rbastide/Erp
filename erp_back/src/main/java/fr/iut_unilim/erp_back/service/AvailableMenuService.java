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
        Connection connection = connectionService.findByIdentifier(username);
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

        if (isLeaf && !hasPermission) {
            return Optional.empty();
        }

        return Optional.of(convertEntityToResponse(menu, role));
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
