package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.SaeRequest;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Sae;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.SaeRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void deleteSaeById(Long id) {
        if (!saeRepository.existsById(id)) {
            return;
        }
        saeRepository.deleteById(id);
    }

    public boolean saveFromDto(@NotNull SaeRequest saeRequest, @NotNull Authentication authentication) {
        if (saeRequest.getSaeID() == null) {
            saveNewSaeFromDto(saeRequest, authentication);
            return true;
        }

        return editExistingSaeFromDto(saeRequest);
    }

    private boolean editExistingSaeFromDto(@NotNull SaeRequest saeRequest) {
        Optional<Sae> existingSaes = saeRepository.findById(saeRequest.getSaeID());

        if (existingSaes.isEmpty()) return false;
        Sae saeToUpdate = existingSaes.get();
        saeToUpdate.setNum(saeRequest.getNum());
        saeToUpdate.setTitle(saeRequest.getTitle());
        saeRepository.save(saeToUpdate);
        return true;
    }

    private void saveNewSaeFromDto(@NotNull SaeRequest saeRequest, @NotNull Authentication authentication) {
        Sae newSae = new Sae();
        newSae.setNum(saeRequest.getNum());
        newSae.setTitle(saeRequest.getTitle());
        handleDepartment(newSae, authentication);
        saeRepository.save(newSae);
    }

    private void handleDepartment(Sae sae, Authentication authentication) {
        Connection connection = connectionService.findByIdentifier(authentication.getName());

        if (connection == null) return;

        sae.setUniversityDepartment(connection.getUniversityDepartment());
    }
}
