package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.SaeDto;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Sae;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.repository.SaeRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<SaeDto> getAllSaesFromDepartment(@NotNull String identifier) {
        Optional<Connection> senderConnection = connectionService.findByIdentifier(identifier);
        if (senderConnection.isEmpty()) return new ArrayList<>();
        UniversityDepartment department = senderConnection.get().getUniversityDepartment();

        List<Sae> saes = saeRepository.findAllByUniversityDepartment(department);

        return convertToSaeDto(saes);
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

    public boolean saveFromDto(@NotNull SaeDto saeDto, @NotNull Authentication authentication) {
        if (saeDto.saeID() == null) {
            saveNewSaeFromDto(saeDto, authentication);
            return true;
        }

        return editExistingSaeFromDto(saeDto);
    }

    private boolean editExistingSaeFromDto(@NotNull SaeDto saeDto) {
        Optional<Sae> existingSaes = saeRepository.findById(saeDto.saeID());

        if (existingSaes.isEmpty()) return false;
        Sae saeToUpdate = existingSaes.get();
        saeToUpdate.setNum(saeDto.num());
        saeToUpdate.setTitle(saeDto.title());
        saeRepository.save(saeToUpdate);
        return true;
    }

    private void saveNewSaeFromDto(@NotNull SaeDto saeDto, @NotNull Authentication authentication) {
        Sae newSae = new Sae();
        newSae.setNum(saeDto.num());
        newSae.setTitle(saeDto.title());
        handleDepartment(newSae, authentication);
        saeRepository.save(newSae);
    }

    private void handleDepartment(Sae sae, Authentication authentication) {
        Optional<Connection> connection = connectionService.findByIdentifier(authentication.getName());

        if (connection.isEmpty()) return;

        sae.setUniversityDepartment(connection.get().getUniversityDepartment());
    }

    private List<SaeDto> convertToSaeDto(List<Sae> saes) {
        List<SaeDto> saesDto = new ArrayList<>();
        for (Sae sae: saes) {
            saesDto.add(new SaeDto(sae.getSaeID(), sae.getNum(), sae.getTitle()));
        }

        return saesDto;
    }
}
