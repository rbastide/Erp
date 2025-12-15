package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.service.ResourceSheetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/resourceSheet")
public class ResourceSheetController {

    private ResourceSheetService resourceSheetService;

    public ResourceSheetController(ResourceSheetService resourceSheetService) {
        this.resourceSheetService = resourceSheetService;
    }
}
