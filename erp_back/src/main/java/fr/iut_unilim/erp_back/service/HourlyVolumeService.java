package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.repository.HourlyVolumeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HourlyVolumeService {

    private final HourlyVolumeRepository hourlyVolumeRepository;

    public HourlyVolumeService(HourlyVolumeRepository hourlyVolumeRepository) {
        this.hourlyVolumeRepository = hourlyVolumeRepository;
    }
}
