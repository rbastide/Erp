package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.MenuResponse;
import fr.iut_unilim.erp_back.entity.AvailableMenu;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Role;
import fr.iut_unilim.erp_back.repository.AvailableMenuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<AvailableMenu> menus = availableMenuRepository.findAll();
        for (AvailableMenu menu : menus) {
            String permissionKey = menu.getPermissionKey();
            boolean hasPermission = permissionService.hasPrivilege(role, permissionKey);

            if (hasPermission) {
                authorizedMenus.add(convertEntityToResponse(menu));
            }
        }

        return authorizedMenus;
    }

    private MenuResponse convertEntityToResponse(AvailableMenu menu) {
        return new MenuResponse(menu.getId(), menu.getLabel(), menu.getRoute(), menu.getIconId());
    }
}
