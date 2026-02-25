package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.EditRolePermissionRequest;
import fr.iut_unilim.erp_back.dto.PermissionResponse;
import fr.iut_unilim.erp_back.entity.Permission;
import fr.iut_unilim.erp_back.entity.PermissionDefinition;
import fr.iut_unilim.erp_back.repository.PermissionDefinitionRepository;
import fr.iut_unilim.erp_back.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    public boolean editRolePermission(EditRolePermissionRequest editRolePermissionRequest) {
        Optional<Permission> permissionOptional = permissionRepository.findById(editRolePermissionRequest.permissionRoleId());
        Optional<PermissionDefinition> permissionDefinitionOptional = permissionDefinitionRepository.findById(editRolePermissionRequest.permissionId());

        if (permissionOptional.isEmpty() || permissionDefinitionOptional.isEmpty()) return false;

        Permission permission = permissionOptional.get();
        PermissionDefinition permissionDefinition = permissionDefinitionOptional.get();

        BitSet bitSet = permission.getBitSet();
        int permIndex = permissionDefinition.getPermissionDefinitionBitIndex();
        boolean newState = editRolePermissionRequest.permissionState();

        bitSet.set(permIndex, newState);

        permission.setBitSet(bitSet);
        permissionRepository.save(permission);

        return true;
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
