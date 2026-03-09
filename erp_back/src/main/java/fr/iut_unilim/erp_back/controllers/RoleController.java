package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.EditCreateRoleRequest;
import fr.iut_unilim.erp_back.entity.Role;
import fr.iut_unilim.erp_back.service.PermissionService;
import fr.iut_unilim.erp_back.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final PermissionService permissionService;
    private final RoleService roleService;

    public RoleController(PermissionService permissionService, RoleService roleService) {
        this.permissionService = permissionService;
        this.roleService = roleService;
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

    @GetMapping("/all")
    @PreAuthorize("@securityService.hasPermission('PERM_MANAGEMENT')")
    public ResponseEntity<?> getRoles() {
        List<Role> roles = roleService.getAllRole();
        return ResponseEntity.ok(roleService.convertEntitiesToResponses(roles));
    }
}
