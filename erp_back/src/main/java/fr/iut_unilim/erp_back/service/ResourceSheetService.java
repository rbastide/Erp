package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.ErpBackApplication;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.ResourceSheet;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.ResourceSheetRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceSheetService {

    private final ResourceSheetRepository resourceSheetRepository;
    private final ConnectionService connectionService;

    public ResourceSheetService(ResourceSheetRepository resourceSheetRepository, ConnectionService connectionService) {
        this.resourceSheetRepository = resourceSheetRepository;
        this.connectionService = connectionService;
    }

    public List<ResourceSheet> getAllResourceSheets() {
        return resourceSheetRepository.findAll();
    }

    public List<ResourceSheet> getAllResourceSheetsFromDepartment(@NotNull String identifier) {
        Connection senderConnection = connectionService.findByIdentifier(identifier);
        UniversityDepartment department = senderConnection.getUniversityDepartment();
        ErpBackApplication.LOGGER.info("Department : " + department);

        return resourceSheetRepository.findAllByUniversityDepartment(department);
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
