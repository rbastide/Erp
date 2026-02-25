package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.PermissionDefinitionResponse;
import fr.iut_unilim.erp_back.service.PermissionDefinitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perm")
public class PermissionController {
    private final PermissionDefinitionService permissionDefinitionService;

    public PermissionController(PermissionDefinitionService permissionDefinitionService) {
        this.permissionDefinitionService = permissionDefinitionService;
    }

    @GetMapping("/perms")
    public ResponseEntity<?> getAllPerms() {
        List<PermissionDefinitionResponse> permissions = permissionDefinitionService.getAllPermissionDefinition();

        return ResponseEntity.ok(permissions);
    }
}
