package fr.iut_unilim.erp_back.loaders;

import fr.iut_unilim.erp_back.entity.ClassType;
import fr.iut_unilim.erp_back.repository.ClassTypeRepository;
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

        ClassType cmClassType = new ClassType();
        cmClassType.setClassTypeName("CM");
        classTypeRepository.save(cmClassType);

        ClassType dsClassType = new ClassType();
        dsClassType.setClassTypeName("DS");
        classTypeRepository.save(dsClassType);

        ClassType dstpClassType = new ClassType();
        dstpClassType.setClassTypeName("DS/TP");
        classTypeRepository.save(dstpClassType);

        ClassType tdClassType = new ClassType();
        tdClassType.setClassTypeName("TD");
        classTypeRepository.save(tdClassType);

        ClassType tpClassType = new ClassType();
        tpClassType.setClassTypeName("TP");
        classTypeRepository.save(tpClassType);
    }
}
