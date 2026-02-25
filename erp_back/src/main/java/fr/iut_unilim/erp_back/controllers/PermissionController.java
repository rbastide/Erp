package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.ErpBackApplication;
import fr.iut_unilim.erp_back.dto.PermissionDefinitionResponse;
import fr.iut_unilim.erp_back.dto.PermissionResponse;
import fr.iut_unilim.erp_back.service.PermissionDefinitionService;
import fr.iut_unilim.erp_back.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perm")
public class PermissionController {
    private final PermissionDefinitionService permissionDefinitionService;
    private final PermissionService permissionService;

    public PermissionController(PermissionDefinitionService permissionDefinitionService, PermissionService permissionService) {
        this.permissionDefinitionService = permissionDefinitionService;
        this.permissionService = permissionService;
    }

    @GetMapping("/perms")
    public ResponseEntity<?> getAllAvailablePerms() {
        List<PermissionDefinitionResponse> permissions = permissionDefinitionService.getAllPermissionDefinition();

        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/perms/role")
    public ResponseEntity<?> getAllRolesPerms() {
        List<PermissionResponse> permissionResponses = permissionService.getAllRolePermissions();

        return ResponseEntity.ok(permissionResponses);
    }
}
