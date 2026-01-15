package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.ResourceResponse;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.repository.ResourceRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<Resource> getFromNum(String num) {
        return resourceRepository.findByNum(num);
    }

    public List<ResourceResponse> getAllResources() {
        List<Resource> resources = resourceRepository.findAll();
        List<ResourceResponse> resourceResponses = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceResponse resourceResponse = new ResourceResponse(resource.getResourceID(),resource.getNum(),resource.getName(),resource.getSemester(),resource.getUniversityDepartment());
            resourceResponses.add(resourceResponse);
        }
        return resourceResponses;
    }

    public Optional<Resource> getResourceById(@NotNull Long id) {
        return resourceRepository.findById(id);
    }
}