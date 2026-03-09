package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.ResourceDto;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.service.ConnectionService;
import fr.iut_unilim.erp_back.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;
    private final ConnectionService connectionService;

    public ResourceController(ResourceService resourceService, ConnectionService connectionService) {
        this.resourceService = resourceService;
        this.connectionService = connectionService;
    }

    @GetMapping("/resources")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_MANAGEMENT')")
    public ResponseEntity<?> getResource(Authentication authentication) {
        return ResponseEntity.ok(resourceService.getAllResourceFromDepartment(authentication.getName()));
    }

    @PostMapping("/edit-resource")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_MANAGEMENT')")
    public ResponseEntity<?> createAndEditResource(@RequestBody ResourceDto res, Authentication authentication) {
        Connection connection = connectionService.findByIdentifier(authentication.getName());

        if (connection == null) return ResponseEntity.badRequest().build();

        final boolean resourceHasBeenSaved = resourceService.saveFromDto(res, connection.getUniversityDepartment());

        if (!resourceHasBeenSaved) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body("Ressource traitée avec succès.");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityService.hasPermission('RESOURCE_MANAGEMENT')")
    public ResponseEntity<?> deleteResource(@PathVariable Long id) {
        boolean doResourceHasBeenDeleted = resourceService.deleteResourceById(id);

        if (!doResourceHasBeenDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("La ressource a bien été supprimée.");
    }
}
