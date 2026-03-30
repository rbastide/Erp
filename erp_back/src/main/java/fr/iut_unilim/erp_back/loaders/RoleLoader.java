package fr.iut_unilim.erp_back.loaders;

import fr.iut_unilim.erp_back.entity.Role;
import fr.iut_unilim.erp_back.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RoleLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RoleLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() != 0) return;

        Role superAdminRole = new Role();
        superAdminRole.setRoleName("Super-Admin");
        roleRepository.save(superAdminRole);

        Role adminRole = new Role();
        adminRole.setRoleName("Administrateur");
        roleRepository.save(adminRole);

        Role teacherRole = new Role();
        teacherRole.setRoleName("Professeur");
        roleRepository.save(teacherRole);

        Role tempTeacherRole = new Role();
        tempTeacherRole.setRoleName("Vacataire");
        roleRepository.save(tempTeacherRole);
    }
}
