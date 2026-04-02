package fr.iut_unilim.erp_back.loaders;

import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.UniversityDepartmentRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Order(2)
public class UniversityDepartmentLoader implements CommandLineRunner {
    private final UniversityDepartmentRepository universityDepartmentRepository;

    private static final String UNIVERSITY_NAME = "IUT du Limousin";
    private static final String CITY_LIMOGES = "Limoges";

    public UniversityDepartmentLoader(UniversityDepartmentRepository universityDepartmentRepository) {
        this.universityDepartmentRepository = universityDepartmentRepository;
    }

    @Override
    public void run(String... args) {
        if (universityDepartmentRepository.count() != 0) return;

        saveDepartment("Informatique");
        saveDepartment("Mesure Physique");
        saveDepartment("Gestion des Entreprises et des Administrations");
        saveDepartment("Techniques de Commercialisation");
        saveDepartment("Génie Mécanique et Productique");
    }

    private void saveDepartment(@NotNull String name) {
        UniversityDepartment dept = new UniversityDepartment();
        dept.setUniversityName(UNIVERSITY_NAME);
        dept.setUniversityDepartmentName(name);
        dept.setCity(UniversityDepartmentLoader.CITY_LIMOGES);
        dept.setCreationDate(new Date());
        universityDepartmentRepository.save(dept);
    }
}
