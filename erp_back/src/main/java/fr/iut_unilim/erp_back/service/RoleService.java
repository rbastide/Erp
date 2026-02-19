package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Role;
import fr.iut_unilim.erp_back.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createOrAccessRoleByRoleName(String roleName) {
        final Optional<Role> canHaveRole = roleRepository.findByRoleName(roleName);

        return canHaveRole.orElseGet(
                () -> {
                    Role role = new Role();
                    role.setRoleName(roleName);
                    return roleRepository.save(role);
                }
        );
    }
}
