package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.EditCreateRoleRequest;
import fr.iut_unilim.erp_back.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final PermissionService permissionService;

    public RoleController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    @PreAuthorize("@securityService.hasPermission('PERM_MANAGEMENT')")
    public ResponseEntity<?> createRole(@RequestBody EditCreateRoleRequest createRoleRequest) {
        boolean hasBeenCreated = permissionService.createEditPermission(createRoleRequest);

        if (!hasBeenCreated) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(permissionService.createEditPermission(createRoleRequest));
    }
}
