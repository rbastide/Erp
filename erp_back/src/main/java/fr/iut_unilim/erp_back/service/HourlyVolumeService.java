package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.HourlyVolume;
import fr.iut_unilim.erp_back.repository.HourlyVolumeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class HourlyVolumeService {

    private final HourlyVolumeRepository hourlyVolumeRepository;

    public HourlyVolumeService(HourlyVolumeRepository hourlyVolumeRepository) {
        this.hourlyVolumeRepository = hourlyVolumeRepository;
    }

    public List<HourlyVolume> getAllHourlyVolumesFromDatas(int hoursCM, int hoursTD, int hoursTP, int hoursDSTP) {
        return hourlyVolumeRepository.findByDatas(hoursCM, hoursTD, hoursTP, hoursDSTP);
    }

    public void save(HourlyVolume hourlyVolume) {
        hourlyVolumeRepository.save(hourlyVolume);
    }

    public List<HourlyVolume> getAllHourlyVolume() {
        return hourlyVolumeRepository.findAll();
    }

    public Optional<HourlyVolume> findById(Long id) {
        return hourlyVolumeRepository.findById(id);

    }
}