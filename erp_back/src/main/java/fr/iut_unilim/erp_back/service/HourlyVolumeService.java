package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.HourlyVolume;
import fr.iut_unilim.erp_back.repository.HourlyVolumeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HourlyVolumeService {

    private final HourlyVolumeRepository hourlyVolumeRepository;

    public HourlyVolumeService(HourlyVolumeRepository hourlyVolumeRepository) {
        this.hourlyVolumeRepository = hourlyVolumeRepository;
    }

    public List<HourlyVolume> getAllHourlyVolumesFromDatas(Float hoursCM, Float hoursTD, Float hoursTP, Float hoursDSTP) {
        return hourlyVolumeRepository.findByDatas(hoursCM, hoursTD, hoursTP, hoursDSTP);
    }

    public HourlyVolume save(HourlyVolume hourlyVolume) {
        hourlyVolumeRepository.save(hourlyVolume);
        return hourlyVolume;
    }

    public List<HourlyVolume> getAllHourlyVolume() {
        return hourlyVolumeRepository.findAll();
    }

    public Optional<HourlyVolume> findById(Long id) {
        return hourlyVolumeRepository.findById(id);
    }
}