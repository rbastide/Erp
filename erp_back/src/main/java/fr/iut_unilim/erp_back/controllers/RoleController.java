package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.CreateRoleRequest;
import fr.iut_unilim.erp_back.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final PermissionService permissionService;

    public RoleController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody CreateRoleRequest createRoleRequest) {
        return ResponseEntity.ok(permissionService.createPermission(createRoleRequest));
    }
}
