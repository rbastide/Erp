package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.ResourceResponse;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
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

    public ResourceService(ResourceRepository resourceRepository, ConnectionService connectionService) {
        this.resourceRepository = resourceRepository;
        this.connectionService = connectionService;
    }

    public List<Resource> getFromNum(String num) {
        return resourceRepository.findByNum(num);
    }

    public List<ResourceResponse> getAllResources() {
        List<Resource> resources = resourceRepository.findAll();
        return convertToResourceResponse(resources);
    }

    public List<ResourceResponse> getAllResourceFromDepartment(@NotNull String identifier) {
        Connection senderConnection = connectionService.findByIdentifier(identifier);
        UniversityDepartment department = senderConnection.getUniversityDepartment();

        List<Resource> resources = resourceRepository.findAllByUniversityDepartment(department);
        return convertToResourceResponse(resources);
    }

    public Optional<Resource> getResourceById(@NotNull Long id) {
        return resourceRepository.findById(id);
    }

    private List<ResourceResponse> convertToResourceResponse(List<Resource> resources) {
        List<ResourceResponse> resourceResponses = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceResponse resourceResponse = convertToResourceResponse(resource);
            resourceResponses.add(resourceResponse);
        }
        return resourceResponses;
    }

    private ResourceResponse convertToResourceResponse(Resource resource) {
        return new ResourceResponse(resource.getResourceID(), resource.getNum(), resource.getName(), resource.getSemester(), resource.getUniversityDepartment());
    }
}