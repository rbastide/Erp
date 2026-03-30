package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.ResourceDto;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Mccc;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.McccRepository;
import fr.iut_unilim.erp_back.repository.ResourceRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ConnectionService connectionService;
    private final McccRepository mcccRepository;

    public ResourceService(ResourceRepository resourceRepository, ConnectionService connectionService, McccRepository mcccRepository) {
        this.resourceRepository = resourceRepository;
        this.connectionService = connectionService;
        this.mcccRepository = mcccRepository;
    }

    public boolean saveFromDto(@NotNull ResourceDto res, @NotNull UniversityDepartment department) {
        if (res.getResourceID() != null) {
            return editResource(res);
        }
        createNewResource(res, department);

        return true;
    }

    public boolean deleteResourceById(@NotNull Long id) {
        Optional<Resource> resource = resourceRepository.findById(id);
        if (resource.isEmpty()) {
            return false;
        }
        List<Mccc> mccc = mcccRepository.findByResourceId(resource.get());
        mcccRepository.deleteAll(mccc);
        resourceRepository.deleteById(id);
        return true;
    }

    private void createNewResource(ResourceDto res, UniversityDepartment department) {
        Resource newResource = new Resource();
        assignResourceValuesFromDto(newResource, res);
        newResource.setUniversityDepartment(department);
        resourceRepository.save(newResource);
    }

    private boolean editResource(ResourceDto res) {
        Optional<Resource> existingResource = resourceRepository.findById(res.getResourceID());

        if (existingResource.isPresent()) {
            Resource resourceToUpdate = existingResource.get();
            assignResourceValuesFromDto(resourceToUpdate, res);
            resourceRepository.save(resourceToUpdate);
            return true;
        }
        return false;
    }

    private void assignResourceValuesFromDto(Resource resource, ResourceDto res) {
        resource.setNum(res.getNum());
        resource.setName(res.getName());
        resource.setSemester(res.getSemester());
    }

    public List<ResourceDto> getAllResourceFromDepartment(@NotNull String identifier) {
        Optional<Connection> senderConnectionOptional = connectionService.findByIdentifier(identifier);
        if (senderConnectionOptional.isEmpty()) return new ArrayList<>();
        Connection senderConnection = senderConnectionOptional.get();
        UniversityDepartment department = senderConnection.getUniversityDepartment();

        List<Resource> resources = resourceRepository.findAllByUniversityDepartment(department);
        return convertToResourceResponse(resources);
    }

    public Optional<Resource> getResourceById(@NotNull Long id) {
        return resourceRepository.findById(id);
    }

    private List<ResourceDto> convertToResourceResponse(List<Resource> resources) {
        List<ResourceDto> resourceResponses = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceDto resourceResponse = convertToResourceResponse(resource);
            resourceResponses.add(resourceResponse);
        }
        return resourceResponses;
    }

    private ResourceDto convertToResourceResponse(Resource resource) {
        return new ResourceDto(resource.getResourceID(), resource.getNum(), resource.getName(), resource.getSemester(), resource.getUniversityDepartment());
    }
}