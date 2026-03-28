package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Skill;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.SkillRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final ConnectionService connectionService;

    public SkillService(SkillRepository skillRepository, ConnectionService connectionService) {
        this.skillRepository = skillRepository;
        this.connectionService = connectionService;
    }

    public List<Skill> getSkillsByNum(int skillNum) {
        return skillRepository.findBySkillNum(skillNum);
    }

    public boolean doSkillNumExists(int skillNum) {
        return !getSkillsByNum(skillNum).isEmpty();
    }

    public Optional<Skill> getSkillsFromId(Long id) {
        return skillRepository.findById(id);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public List<Skill> getAllSkillsFromDepartment(@NotNull String identifier) {
        Optional<Connection> senderConnection = connectionService.findByIdentifier(identifier);
        if (senderConnection.isEmpty()) return new ArrayList<>();
        UniversityDepartment department = senderConnection.get().getUniversityDepartment();

        return skillRepository.findAllByUniversityDepartment(department);
    }
}
