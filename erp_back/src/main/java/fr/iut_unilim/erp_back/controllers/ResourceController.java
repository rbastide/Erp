package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.ResourceResponse;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Mccc;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.repository.McccRepository;
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
    private final McccRepository mcccRepository;

    public ResourceController(ResourceService resourceService, ResourceRepository resourceRepository, ConnectionService connectionService, McccRepository mcccRepository) {
        this.resourceService = resourceService;
        this.resourceRepository = resourceRepository;
        this.connectionService = connectionService;
        this.mcccRepository = mcccRepository;
    }

    @GetMapping("/resources")
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public ResponseEntity<?> getResource(Authentication authentication) {
        return ResponseEntity.ok(resourceService.getAllResourceFromDepartment(authentication.getName()));
    }

    @PostMapping("/editResource")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> editResources(@RequestBody ResourceResponse res, Authentication authentication) {
        if (res.getResourceID() != null) {
            Optional<Resource> existingResource = resourceRepository.findById(res.getResourceID());

            if (existingResource.isPresent()) {
                Resource resourceToUpdate = existingResource.get();
                resourceToUpdate.setNum(res.getNum());
                resourceToUpdate.setName(res.getName());
                resourceToUpdate.setSemester(res.getSemester());
                resourceRepository.save(resourceToUpdate);
                return ResponseEntity.ok().body("Ressource traitée avec succès");
            }
        }
        Resource newResource = new Resource();
        newResource.setNum(res.getNum());
        newResource.setName(res.getName());
        newResource.setSemester(res.getSemester());
        handleDepartment(newResource, authentication);
        resourceRepository.save(newResource);
        return ResponseEntity.ok().body("Ressource traitée avec succès");
    }

    private void handleDepartment(Resource resource, Authentication authentication) {
        Connection connection = connectionService.findByIdentifier(authentication.getName());

        if (connection == null) return;

        resource.setUniversityDepartment(connection.getUniversityDepartment());
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteResource(@PathVariable Long id) {
        Optional<Resource> resource = resourceRepository.findById(id);
        if (resource.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Mccc> mccc = mcccRepository.findByResourceId(resource.get());
        mcccRepository.deleteAll(mccc);
        resourceRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
