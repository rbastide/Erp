package fr.iut_unilim.erp_back.loaders;

import fr.iut_unilim.erp_back.entity.PermissionDefinition;
import fr.iut_unilim.erp_back.repository.PermissionDefinitionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PermissionDefinitionLoader implements CommandLineRunner {
    private final PermissionDefinitionRepository permissionDefinitionRepository;

    public PermissionDefinitionLoader(PermissionDefinitionRepository permissionDefinitionRepository) {
        this.permissionDefinitionRepository = permissionDefinitionRepository;
    }

    @Override
    public void run(String... args) {
        if (permissionDefinitionRepository.count() != 0) return;

        savePermissionDefinition(0, "Gestion des utilisateurs", "USER_MANAGEMENT");
        savePermissionDefinition(1, "Gestion des SAE", "SAE_MANAGEMENT");
        savePermissionDefinition(2, "Gestion des permissions", "PERM_MANAGEMENT");
        savePermissionDefinition(3, "Gestion des MCCC", "MCCC_MANAGEMENT");
        savePermissionDefinition(4, "Gestion des fiches ressources", "RESOURCE_SHEET_MANAGEMENT");
        savePermissionDefinition(5, "Gestion des ressources", "RESOURCE_MANAGEMENT");
        savePermissionDefinition(6, "Gestion des compétences", "SKILL_MANAGEMENT");
        savePermissionDefinition(7, "Gestion des départements", "DEPARTMENT_MANAGEMENT");
        savePermissionDefinition(8, "Remplir une fiche ressource", "FILL_RESOURCE_SHEET");
        savePermissionDefinition(9, "Afficher les fiches ressources", "RESOURCE_SHEET_HISTORY");
        savePermissionDefinition(10, "Valider des fiches ressources", "VALIDATE_RESOURCE_SHEET");
        savePermissionDefinition(11, "Gestion des rappels", "RECALL_MANAGEMENT");
    }

    private void savePermissionDefinition(int index, String label, String key) {
        PermissionDefinition permissionDefinition = new PermissionDefinition();
        permissionDefinition.setPermissionDefinitionBitIndex(index);
        permissionDefinition.setPermissionLabel(label);
        permissionDefinition.setPermissionKey(key);
        permissionDefinitionRepository.save(permissionDefinition);
    }
}
