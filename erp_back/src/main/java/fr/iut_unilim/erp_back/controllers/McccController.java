package fr.iut_unilim.erp_back.controllers;


import fr.iut_unilim.erp_back.dto.McccResponse;
import fr.iut_unilim.erp_back.entity.HourlyVolume;
import fr.iut_unilim.erp_back.entity.Mccc;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.service.HourlyVolumeService;
import fr.iut_unilim.erp_back.service.McccService;
import fr.iut_unilim.erp_back.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mccc")
@CrossOrigin(origins = "http://localhost:5173")
public class McccController {

    private final McccService mcccService;
    private final HourlyVolumeService hourlyVolumeService;
    private final ResourceService resourceService;

    public McccController(McccService mcccService, HourlyVolumeService hourlyVolumeService, ResourceService resourceService) {
        this.mcccService = mcccService;
        this.hourlyVolumeService = hourlyVolumeService;
        this.resourceService = resourceService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMccc(@RequestBody McccResponse dto) {
        try {
            Mccc mccc = new Mccc();

            List<HourlyVolume> hourlyVolumes = hourlyVolumeService.getAllHourlyVolumesFromDatas(dto.getHoursCM(),dto.getHoursTD(),dto.getHoursTP(),dto.getHoursDSTP());
            List<Resource> resources = resourceService.getFromName(dto.getResourceCode());
            if(resources.size()==1 ){
                Resource resource = resources.get(0);
                mccc.setResourceId(resource);
            }



            if (hourlyVolumes.size() == 1) {
                HourlyVolume hourlyVolume = hourlyVolumes.get(0);
                hourlyVolume.setNbHoursCM(dto.getHoursCM());
                hourlyVolume.setNbHoursTD(dto.getHoursTD());
                hourlyVolume.setNbHoursTP(dto.getHoursTP());
                hourlyVolume.setNbHoursDS(dto.getHoursDS());
                hourlyVolume.setNbHoursDSTP(dto.getHoursDSTP());
                mccc.setHourlyVolId(hourlyVolume);
            }

            return ResponseEntity.ok("MCCC sauvegardée avec succès !");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }
}