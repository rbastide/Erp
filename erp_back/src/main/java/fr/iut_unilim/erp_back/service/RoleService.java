package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.RoleResponse;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Role;
import fr.iut_unilim.erp_back.entity.RolePermission;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import fr.iut_unilim.erp_back.repository.PermissionRepository;
import fr.iut_unilim.erp_back.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final ConnectionRepository connectionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository, ConnectionRepository connectionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.connectionRepository = connectionRepository;
    }

    public Role createOrAccessRoleByRoleName(String roleName) {
        final Optional<Role> canHaveRole = roleRepository.findByRoleName(roleName);

        return canHaveRole.orElseGet(
                () -> {
                    Role role = new Role();
                    role.setRoleName(roleName);
                    return roleRepository.save(role);
                }
        );
    }

    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    public boolean deleteRole(Long roleId) {
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (roleOptional.isEmpty()) return false;

        List<Connection> connections = connectionRepository.findAllByRole(roleOptional.get());

        if (!connections.isEmpty()) return false;

        Optional<RolePermission> permissionOptional = permissionRepository.findById(roleId);
        if (permissionOptional.isEmpty()) return false;
        permissionRepository.deleteById(roleId);

        roleRepository.deleteById(roleId);

        return true;
    }

    public List<RoleResponse> convertEntitiesToResponses(List<Role> roles) {
        return roles.stream().map(this::convertEntityToResponse).toList();
    }

    public RoleResponse convertEntityToResponse(Role role) {
        return new RoleResponse(role.getId(), role.getRoleName());
    }
}
