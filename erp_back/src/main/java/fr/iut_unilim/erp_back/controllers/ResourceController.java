package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.ResourceResponse;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.repository.ResourceRepository;
import fr.iut_unilim.erp_back.service.ConnectionService;
import fr.iut_unilim.erp_back.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;
    private final ResourceRepository resourceRepository;
    private final ConnectionService connectionService;

    public ResourceController(ResourceService resourceService, ResourceRepository resourceRepository, ConnectionService connectionService) {
        this.resourceService = resourceService;
        this.resourceRepository = resourceRepository;
        this.connectionService = connectionService;
    }

    @GetMapping("/resources")
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public ResponseEntity<?> getResource() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    @PostMapping("/editResource")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> editResources(@RequestBody List<ResourceResponse> resList, Authentication authentication) {
        for (ResourceResponse res : resList) {
            if (res.getResourceID() != null) {
                Optional<Resource> existingResource = resourceRepository.findById(res.getResourceID());

                if (existingResource.isPresent()) {
                    Resource resourceToUpdate = existingResource.get();
                    resourceToUpdate.setNum(res.getNum());
                    resourceToUpdate.setName(res.getName());
                    resourceToUpdate.setSemester(res.getSemestre());
                    resourceRepository.save(resourceToUpdate);
                    continue;
                }
            }
            Resource newResource = new Resource();
            newResource.setNum(res.getNum());
            newResource.setName(res.getName());
            newResource.setSemester(res.getSemestre());
            handleDepartment(newResource, authentication);
            resourceRepository.save(newResource);
        }
        return ResponseEntity.ok().body("Ressources traitées avec succès");
    }

    private void handleDepartment(Resource resource, Authentication authentication) {
        Connection connection = connectionService.findByIdentifier(authentication.getName());

        if (connection == null) return;

        resource.setUniversityDepartment(connection.getUniversityDepartment());
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteResource(@PathVariable Long id) {
        if (!resourceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        resourceRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}