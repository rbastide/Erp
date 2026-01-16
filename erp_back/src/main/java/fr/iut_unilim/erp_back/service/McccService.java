package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.model.McccId;
import fr.iut_unilim.erp_back.repository.McccRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class McccService {
    private final McccRepository mcccRepository;
    private final ConnectionService connectionService;

    public McccService(McccRepository mcccRepository, ConnectionService connectionService) {
        this.mcccRepository = mcccRepository;
        this.connectionService = connectionService;
    }

    public Optional<Mccc> getMcccById(McccId id) {
        return mcccRepository.findById(id);
    }

    public Optional<Mccc> findById(Long id) {
        return mcccRepository.findByMcccId(id);
    }

    public void save(Mccc mccc) {
        mcccRepository.save(mccc);
    }

    public List<Mccc> getAllMccc() {
        return mcccRepository.findAll();
    }

    public List<Mccc> getAllMcccFromDepartment(@NotNull String identifier) {
        Connection senderConnection = connectionService.findByIdentifier(identifier);
        UniversityDepartment department = senderConnection.getUniversityDepartment();

        return mcccRepository.findAllByUniversityDepartment(department);
    }

    @NotNull
    public Optional<Mccc> getCurrentMcccFromResource(Resource resource) {
        return mcccRepository.findFirstByResourceIdOrderByLastModificationDateDesc(resource);
    }

    @Nullable
    public Set<CriticalLearning> getCriticalLearningsByResource(@NotNull Resource resource) {
        Optional<Mccc> currentMccc = getCurrentMcccFromResource(resource);
        return currentMccc.map(Mccc::getCriticalLearningsId).orElse(null);
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

    @Nullable
    public Date getCreationDateFromId(@NotNull Long id) {
        Optional<Mccc> mccc = mcccRepository.findByMcccId(id);
        return mccc.map(Mccc::getCreationDate).orElse(null);
    }

    public List<Long> getTeacherIdsByMcccId(Long mcccId) {
        return mcccRepository.findTeacherIdsByMcccId(mcccId);
    }
}