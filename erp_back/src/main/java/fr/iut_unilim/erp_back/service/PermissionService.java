package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.PermissionResponse;
import fr.iut_unilim.erp_back.entity.Permission;
import fr.iut_unilim.erp_back.entity.PermissionDefinition;
import fr.iut_unilim.erp_back.repository.PermissionDefinitionRepository;
import fr.iut_unilim.erp_back.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionDefinitionRepository permissionDefinitionRepository;
    private final RoleService roleService;

    public PermissionService(PermissionRepository permissionRepository, PermissionDefinitionRepository permissionDefinitionRepository, RoleService roleService) {
        this.permissionRepository = permissionRepository;
        this.permissionDefinitionRepository = permissionDefinitionRepository;
        this.roleService = roleService;
    }

    public List<PermissionResponse> getAllRolePermissions() {
        List<Permission> permissions = permissionRepository.findAll();

        return permissions.stream()
                .map(this::convertEntityToResponse)
                .toList();
    }

    private PermissionResponse convertEntityToResponse(Permission permission) {
        HashMap<Long, Boolean> perms = new HashMap<>();

        BitSet permBits = permission.getBitSet();
        List<PermissionDefinition> permissionDefinitions = permissionDefinitionRepository.findAll();
        for (PermissionDefinition permissionDefinitionResponse : permissionDefinitions) {
            int permIndex = permissionDefinitionResponse.getPermissionDefinitionBitIndex();
            boolean permState = permBits.get(permIndex);
            long permId = permissionDefinitionResponse.getPermissionDefinitionID();
            perms.put(permId, permState);
        }

        return new PermissionResponse(
                permission.getPermissionID(),
                roleService.convertEntityToResponse(permission.getRole()),
                perms
        );
    }
}
