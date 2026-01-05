package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.HourlyVolume;
import fr.iut_unilim.erp_back.repository.HourlyVolumeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HourlyVolumeService {

    private final HourlyVolumeRepository hourlyVolumeRepository;

    public HourlyVolumeService(HourlyVolumeRepository hourlyVolumeRepository) {
        this.hourlyVolumeRepository = hourlyVolumeRepository;
    }

    public List<HourlyVolume> getAllHourlyVolumesFromDatas(int hoursCM, int hoursTD, int hoursTP, int hoursDSTP) {
        return hourlyVolumeRepository.findByDatas(hoursCM, hoursTD, hoursTP, hoursDSTP);
    }
}
