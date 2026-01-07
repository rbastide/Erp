package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Sae;
import fr.iut_unilim.erp_back.entity.Teacher;
import fr.iut_unilim.erp_back.repository.SaeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SaeService {
    private final SaeRepository saeRepository;

    public SaeService(SaeRepository saeRepository) {
        this.saeRepository = saeRepository;
    }

    public List<Sae> getAllSaes() { return saeRepository.findAll(); }

    public List<Sae> getSaeByNum(String num) {
        return saeRepository.findByNum(num);
    }

    public void save(Sae sae) {saeRepository.save(sae);}


}
