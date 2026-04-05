package fr.iut_unilim.erp_back.loaders;

import fr.iut_unilim.erp_back.entity.Role;
import fr.iut_unilim.erp_back.entity.RolePermission;
import fr.iut_unilim.erp_back.repository.PermissionRepository;
import fr.iut_unilim.erp_back.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.BitSet;

@Component
@Order(1)
public class RoleLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleLoader(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() != 0) return;

        saveRole("Super-Admin");
        saveRole("Administrateur");
        saveRole("Professeur");
        saveRole("Vacataire");
    }

    private void saveRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        roleRepository.save(role);

        RolePermission permission = new RolePermission();
        permission.setRole(role);
        permission.setBitSet(new BitSet());
        permissionRepository.save(permission);
    }
}
