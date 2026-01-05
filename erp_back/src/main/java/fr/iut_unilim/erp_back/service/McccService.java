package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Mccc;
import fr.iut_unilim.erp_back.repository.McccRepository;
import fr.iut_unilim.erp_back.tools.datastructures.McccId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class McccService {
    private final McccRepository mcccRepository;

    public McccService(McccRepository mcccRepository) {
        this.mcccRepository = mcccRepository;
    }

    public Optional<Mccc> getMcccById(McccId id) {
        return mcccRepository.findById(id);
    }

    public void saveMccc(Mccc mccc) {
        mcccRepository.save(mccc);
    }
}