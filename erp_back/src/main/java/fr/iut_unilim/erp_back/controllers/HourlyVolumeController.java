package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.entity.HourlyVolume;
import fr.iut_unilim.erp_back.service.HourlyVolumeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/hourlyVolume")
public class HourlyVolumeController {

    private HourlyVolumeService hourlyVolumeService;

    public HourlyVolumeController(HourlyVolumeService hourlyVolumeService) {
        this.hourlyVolumeService = hourlyVolumeService;
    }
}
