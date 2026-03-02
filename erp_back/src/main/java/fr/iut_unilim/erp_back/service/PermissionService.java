package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.CreateRoleRequest;
import fr.iut_unilim.erp_back.dto.EditRolePermissionRequest;
import fr.iut_unilim.erp_back.dto.RolePermissionResponse;
import fr.iut_unilim.erp_back.entity.PermissionDefinition;
import fr.iut_unilim.erp_back.entity.Role;
import fr.iut_unilim.erp_back.entity.RolePermission;
import fr.iut_unilim.erp_back.repository.PermissionDefinitionRepository;
import fr.iut_unilim.erp_back.repository.PermissionRepository;
import fr.iut_unilim.erp_back.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionDefinitionRepository permissionDefinitionRepository;
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    public PermissionService(PermissionRepository permissionRepository, PermissionDefinitionRepository permissionDefinitionRepository, RoleService roleService, RoleRepository roleRepository) {
        this.permissionRepository = permissionRepository;
        this.permissionDefinitionRepository = permissionDefinitionRepository;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    public List<RolePermissionResponse> getAllRolePermissions() {
        List<RolePermission> permissions = permissionRepository.findAll();

        return permissions.stream()
                .map(this::convertEntityToResponse)
                .toList();
    }

    public boolean hasPermission(Long roleId, String permissionKey) {
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        return roleOptional.filter(role -> hasPrivilege(role, permissionKey)).isPresent();
    }

    public RolePermission createPermission(CreateRoleRequest createRoleRequest) {
        Role role = roleService.createOrAccessRoleByRoleName(createRoleRequest.roleName());
        RolePermission permission = new RolePermission();
        permission.setRole(role);
        BitSet permBits = convertMapToBitSet(createRoleRequest.permissions());
        permission.setBitSet(permBits);
        permissionRepository.save(permission);
        return permission;
    }

    public boolean editRolePermission(EditRolePermissionRequest editRolePermissionRequest) {
        Optional<RolePermission> permissionOptional = permissionRepository.findById(editRolePermissionRequest.permissionRoleId());
        Optional<PermissionDefinition> permissionDefinitionOptional = permissionDefinitionRepository.findById(editRolePermissionRequest.permissionId());

        if (permissionOptional.isEmpty() || permissionDefinitionOptional.isEmpty()) return false;

        RolePermission permission = permissionOptional.get();
        PermissionDefinition permissionDefinition = permissionDefinitionOptional.get();

        BitSet permBits = permission.getBitSet();
        int permIndex = permissionDefinition.getPermissionDefinitionBitIndex();
        boolean newState = editRolePermissionRequest.permissionState();

        permBits.set(permIndex, newState);

        permission.setBitSet(permBits);
        permissionRepository.save(permission);

        return true;
    }

    public boolean hasPrivilege(Role role, String permissionKey) {
        PermissionDefinition permissionDefinition = permissionDefinitionRepository.findByPermissionKey(permissionKey);

        if (permissionDefinition == null) return false;

        return hasPrivilege(role, permissionDefinition);
    }

    public boolean hasPrivilege(Role role, PermissionDefinition permissionDefinition) {
        List<RolePermission> permissions = permissionRepository.findByRole(role);
        if (permissions.size() != 1) return false;

        RolePermission permission = permissions.get(0);

        BitSet permBits = permission.getBitSet();
        int permIndex = permissionDefinition.getPermissionDefinitionBitIndex();

        return permBits.get(permIndex);
    }

    private RolePermissionResponse convertEntityToResponse(RolePermission permission) {
        HashMap<Long, Boolean> perms = new HashMap<>();

        BitSet permBits = permission.getBitSet();
        List<PermissionDefinition> permissionDefinitions = permissionDefinitionRepository.findAll();
        for (PermissionDefinition permissionDefinitionResponse : permissionDefinitions) {
            int permIndex = permissionDefinitionResponse.getPermissionDefinitionBitIndex();
            boolean permState = permBits.get(permIndex);
            long permId = permissionDefinitionResponse.getPermissionDefinitionID();
            perms.put(permId, permState);
        }

        return new RolePermissionResponse(
                permission.getPermissionID(),
                roleService.convertEntityToResponse(permission.getRole()),
                perms
        );
    }

    private BitSet convertMapToBitSet(Map<Long, Boolean> perms) {
        BitSet permBits = new BitSet();
        for (Long permId : perms.keySet()) {
            Optional<PermissionDefinition> permissionDefinitionOptional = permissionDefinitionRepository.findById(permId);
            if (permissionDefinitionOptional.isPresent()) {
                int permIndex = permissionDefinitionOptional.get().getPermissionDefinitionBitIndex();
                permBits.set(permIndex, perms.get(permId));
            }
        }
        return permBits;
    }
}
