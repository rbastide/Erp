package fr.iut_unilim.erp_back.loaders;

import fr.iut_unilim.erp_back.entity.AvailableMenu;
import fr.iut_unilim.erp_back.repository.AvailableMenuRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AvailableMenuLoader implements CommandLineRunner {
    private final AvailableMenuRepository availableMenuRepository;

    public AvailableMenuLoader(AvailableMenuRepository availableMenuRepository) {
        this.availableMenuRepository = availableMenuRepository;
    }

    @Override
    public void run(String... args) {
        if (availableMenuRepository.count() != 0) return;

        AvailableMenu parentFiches = availableMenuRepository.save(new AvailableMenu(
                "Fiches ressources", "", "", "3", null
        ));

        AvailableMenu parentUtilisateurs = availableMenuRepository.save(new AvailableMenu(
                "Autorisations", "", "", "1", null
        ));

        AvailableMenu parentPedagogie = availableMenuRepository.save(new AvailableMenu(
                "Pédagogie", "", "", "7", null
        ));

        AvailableMenu parentMccc = availableMenuRepository.save(new AvailableMenu(
                "MCCC", "MCCC_MANAGEMENT", "/mccc-resource", "5", null
        ));
        availableMenuRepository.save(parentMccc);

        availableMenuRepository.save(new AvailableMenu(
                "Remplir une fiche ressource", "FILL_RESOURCE_SHEET", "/resource", "2", parentFiches
        ));
        availableMenuRepository.save(new AvailableMenu(
                "Afficher les fiches ressources", "RESOURCE_SHEET_HISTORY", "/history", "3", parentFiches
        ));

        availableMenuRepository.save(new AvailableMenu(
                "Utilisateurs", "USER_MANAGEMENT", "/users-management", "1", parentUtilisateurs
        ));
        availableMenuRepository.save(new AvailableMenu(
                "Rôles", "PERM_MANAGEMENT", "/role-management", "10", parentUtilisateurs
        ));
        availableMenuRepository.save(new AvailableMenu(
                "Periodicité des rappels", "RECALL_MANAGEMENT", "/recall-management", "11", parentUtilisateurs
        ));

        availableMenuRepository.save(new AvailableMenu(
                "Compétences", "SKILL_MANAGEMENT", "/skill-creating", "7", parentPedagogie
        ));
        availableMenuRepository.save(new AvailableMenu(
                "Ressources", "RESOURCE_MANAGEMENT", "/resources-management", "8", parentPedagogie
        ));
        availableMenuRepository.save(new AvailableMenu(
                "SAE", "SAE_MANAGEMENT", "/sae-management", "9", parentPedagogie
        ));
    }
}