package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<Resource> getFromName(String name) {
        return resourceRepository.findByNum(name);
    }
}
