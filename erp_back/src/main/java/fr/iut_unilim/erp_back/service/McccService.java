package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.CriticalLearning;
import fr.iut_unilim.erp_back.entity.Mccc;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.entity.Skill;
import fr.iut_unilim.erp_back.repository.McccRepository;
import fr.iut_unilim.erp_back.tools.datastructures.McccId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class McccService {
    private final McccRepository mcccRepository;

    public McccService(McccRepository mcccRepository) {
        this.mcccRepository = mcccRepository;
    }

    public Optional<Mccc> getMcccById(McccId id) {
        return mcccRepository.findById(id);
    }

    public void save(Mccc mccc) {
        mcccRepository.save(mccc);
    }

    public List<Mccc> getAllMccc() {
        return mcccRepository.findAll();
    }

    @Nullable
    public Set<CriticalLearning> getCriticalLearningsByResource(@NotNull Resource resource) {
        List<Mccc> mcccs = mcccRepository.findByResourceId(resource);

        if (mcccs.isEmpty()) {
            return null;
        }

        Mccc currentMccc = mcccs.get(0);
        for (Mccc mccc : mcccs) {
            if (mccc.getCreationDate().after(currentMccc.getCreationDate())) {
                currentMccc = mccc;
            }
        }

        return currentMccc.getCriticalLearningsId();
    }

    @Nullable
    public Set<Skill> getSkillsByResource(@NotNull Resource resource) {
        Set<CriticalLearning> criticalLearnings = getCriticalLearningsByResource(resource);

        if (criticalLearnings == null) {
            return null;
        }

        Set<Skill> skills = new HashSet<>();
        for (CriticalLearning criticalLearning : criticalLearnings) {
            skills.add(criticalLearning.getRankID().getSkillID());
        }

        return skills;
    }
}
