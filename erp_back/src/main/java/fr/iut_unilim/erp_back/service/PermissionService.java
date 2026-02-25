package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.repository.PermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }
}
