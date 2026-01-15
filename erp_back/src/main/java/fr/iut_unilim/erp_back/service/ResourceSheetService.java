package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.ResourceSheet;
import fr.iut_unilim.erp_back.repository.ResourceSheetRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceSheetService {

    private final ResourceSheetRepository resourceSheetRepository;

    public ResourceSheetService(ResourceSheetRepository resourceSheetRepository) {
        this.resourceSheetRepository = resourceSheetRepository;
    }

    public List<ResourceSheet> getAllResourceSheets() {
        return resourceSheetRepository.findAll();
    }

    public ResourceSheet save(ResourceSheet resourceSheet) {
        return resourceSheetRepository.save(resourceSheet);
    }

    public void deleteResourceSheetById(Long id){
        if(!resourceSheetRepository.existsById(id)){
            return;
        }
        resourceSheetRepository.deleteById(id);
    }

    public Optional<ResourceSheet> getResourceSheetFromId(@NotNull Long id) {
        return resourceSheetRepository.findById(id);
    }
}
