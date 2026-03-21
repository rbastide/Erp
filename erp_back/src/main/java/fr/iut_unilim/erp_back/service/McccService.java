package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.*;
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

    public Optional<Mccc> findById(Long id) {
        return mcccRepository.findByMcccId(id);
    }

    public void save(Mccc mccc) {
        mcccRepository.save(mccc);
    }

    public List<Mccc> getAllMcccFromDepartmentAndYear(@NotNull String identifier, Integer year) {
        Connection senderConnection = connectionService.findByIdentifier(identifier);
        UniversityDepartment department = senderConnection.getUniversityDepartment();

        return mcccRepository.findAllByUniversityDepartmentAndAcademicYearStart(department, year);
    }

    @NotNull
    public Optional<Mccc> getCurrentMcccFromResource(Resource resource) {
        return mcccRepository.findFirstByResourceIdOrderByLastModificationDateDesc(resource);
    }

    @Nullable
    public Set<CriticalConcept> getCriticalConceptsByResource(@NotNull Resource resource) {
        Optional<Mccc> currentMccc = getCurrentMcccFromResource(resource);
        return currentMccc.map(Mccc::getCriticalConceptsId).orElse(null);
    }

    @Nullable
    public Set<Skill> getSkillsByResource(@NotNull Resource resource) {
        Set<CriticalConcept> criticalConcepts = getCriticalConceptsByResource(resource);

        if (criticalConcepts == null) {
            return null;
        }

        Set<Skill> skills = new HashSet<>();
        for (CriticalConcept criticalConcept : criticalConcepts) {
            skills.add(criticalConcept.getRankID().getSkillID());
        }

        return skills;
    }

    @Nullable
    public Date getCreationDateFromId(@NotNull Long id) {
        Optional<Mccc> mccc = mcccRepository.findByMcccId(id);
        return mccc.map(Mccc::getCreationDate).orElse(null);
    }

    public List<Long> getTeacherIdsByMcccId(Long mcccId) {
        Optional<Mccc> mccc = mcccRepository.findByMcccId(mcccId);
        if (mccc.isEmpty()) {
            return Collections.emptyList();
        }

        Mccc mcccEntity = mccc.get();
        Set<TeacherResource> teacherResources = mcccEntity.getTeacherResources();
        return teacherResources.stream().map(TeacherResource::getConnection).map(Connection::getId).toList();
    }

    public Set<Integer> getAllYears() {
        List<Mccc> mcccs = mcccRepository.findAll();
        Set<Integer> years = new HashSet<>();
        for (Mccc mccc : mcccs) {
            years.add(mccc.getAcademicYearStart());
        }
        return years;
    }


}