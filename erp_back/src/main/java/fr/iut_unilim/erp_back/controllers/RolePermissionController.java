package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.ErpBackApplication;
import fr.iut_unilim.erp_back.dto.EditRolePermissionRequest;
import fr.iut_unilim.erp_back.dto.PermissionDefinitionResponse;
import fr.iut_unilim.erp_back.dto.RolePermissionResponse;
import fr.iut_unilim.erp_back.service.PermissionDefinitionService;
import fr.iut_unilim.erp_back.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perm")
public class RolePermissionController {
    private final PermissionDefinitionService permissionDefinitionService;
    private final PermissionService permissionService;

    public RolePermissionController(PermissionDefinitionService permissionDefinitionService, PermissionService permissionService) {
        this.permissionDefinitionService = permissionDefinitionService;
        this.permissionService = permissionService;
    }

    @GetMapping("/perms")
    @PreAuthorize("@securityService.hasPermission('PERM_MANAGEMENT')")
    public ResponseEntity<?> getAllAvailablePerms() {
        List<PermissionDefinitionResponse> permissions = permissionDefinitionService.getAllPermissionDefinition();

        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/perms/roles")
    @PreAuthorize("@securityService.hasPermission('PERM_MANAGEMENT')")
    public ResponseEntity<?> getAllRolesPerms() {
        List<RolePermissionResponse> permissionResponses = permissionService.getAllRolePermissions();

        return ResponseEntity.ok(permissionResponses);
    }

    @PostMapping("/perms/role")
    @PreAuthorize("@securityService.hasPermission('PERM_MANAGEMENT')")
    public ResponseEntity<?> editRolePermissions(@RequestBody List<EditRolePermissionRequest> editRolePermissionRequests) {
        ErpBackApplication.LOGGER.info("test");
        boolean doErrorOccured = false;
        for (EditRolePermissionRequest editRolePermissionRequest : editRolePermissionRequests) {
            doErrorOccured |= !permissionService.editRolePermission(editRolePermissionRequest);
        }

        if (doErrorOccured) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/perms/role/{roleId}/{permissionKey}")
    @PreAuthorize("@securityService.hasPermission('PERM_MANAGEMENT')")
    public ResponseEntity<?> getRolePermissions(@PathVariable Long roleId, @PathVariable String permissionKey) {
        return ResponseEntity.ok(permissionService.hasPermission(roleId, permissionKey));
    }
}
