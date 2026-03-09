package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.PermissionDefinitionResponse;
import fr.iut_unilim.erp_back.entity.PermissionDefinition;
import fr.iut_unilim.erp_back.repository.PermissionDefinitionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionDefinitionService {
    private final PermissionDefinitionRepository permissionDefinitionRepository;

    public PermissionDefinitionService(PermissionDefinitionRepository permissionDefinitionRepository) {
        this.permissionDefinitionRepository = permissionDefinitionRepository;
    }

    public List<PermissionDefinitionResponse> getAllPermissionDefinition() {
        List<PermissionDefinition> permissions = permissionDefinitionRepository.findAll();

        return permissions.stream()
                .map(this::convertEntityToResponse)
                .toList();
    }

    private PermissionDefinitionResponse convertEntityToResponse(PermissionDefinition permissionDefinition) {
        return new PermissionDefinitionResponse(
                permissionDefinition.getPermissionDefinitionID(),
                permissionDefinition.getPermissionLabel(),
                permissionDefinition.getPermissionKey()
        );
    }
}
