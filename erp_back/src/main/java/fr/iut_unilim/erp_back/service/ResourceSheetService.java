package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.ResourceSheet;
import fr.iut_unilim.erp_back.repository.ResourceSheetRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResourceSheetService {

    private final ResourceSheetRepository resourceSheetRepository;

    public ResourceSheetService(ResourceSheetRepository resourceSheetRepository) {
        this.resourceSheetRepository = resourceSheetRepository;
    }

    public List<ResourceSheet> getAllResourceSheets() {
        return resourceSheetRepository.findAll();
    }
}
