package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.service.HourlyVolumeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hourlyVolume")
public class HourlyVolumeController {

    private HourlyVolumeService hourlyVolumeService;

    public HourlyVolumeController(HourlyVolumeService hourlyVolumeService) {
        this.hourlyVolumeService = hourlyVolumeService;
    }
}
