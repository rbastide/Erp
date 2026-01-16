package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Sae;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.SaeRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SaeService {
    private final SaeRepository saeRepository;
    private final ConnectionService connectionService;

    public SaeService(SaeRepository saeRepository, ConnectionService connectionService) {
        this.saeRepository = saeRepository;
        this.connectionService = connectionService;
    }

    public List<Sae> getAllSaes() { return saeRepository.findAll(); }

    public List<Sae> getAllSaesFromDepartment(@NotNull String identifier) {
        Connection senderConnection = connectionService.findByIdentifier(identifier);
        UniversityDepartment department = senderConnection.getUniversityDepartment();

        return saeRepository.findAllByUniversityDepartment(department);
    }

    public List<Sae> getSaeByNum(String num) {
        return saeRepository.findByNum(num);
    }

    public Sae addSae(Sae sae) { return saeRepository.save(sae); }

    public void deleteSaeById(Long id) {
        if (!saeRepository.existsById(id)) {
            return;
        }
        saeRepository.deleteById(id);
    }
}
