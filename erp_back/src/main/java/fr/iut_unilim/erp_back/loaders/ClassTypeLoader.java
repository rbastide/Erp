package fr.iut_unilim.erp_back.loaders;

import fr.iut_unilim.erp_back.entity.ClassType;
import fr.iut_unilim.erp_back.repository.ClassTypeRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ClassTypeLoader implements CommandLineRunner {
    private final ClassTypeRepository classTypeRepository;

    public ClassTypeLoader(ClassTypeRepository classTypeRepository) {
        this.classTypeRepository = classTypeRepository;
    }

    @Override
    public void run(String... args) {
        if (classTypeRepository.count() != 0) return;

        saveClassType("CM");
        saveClassType("DS");
        saveClassType("DS/TP");
        saveClassType("TD");
        saveClassType("TP");
    }

    private void saveClassType(@NotNull String classTypeName) {
        ClassType cmClassType = new ClassType();
        cmClassType.setClassTypeName(classTypeName);
        classTypeRepository.save(cmClassType);
    }
}
