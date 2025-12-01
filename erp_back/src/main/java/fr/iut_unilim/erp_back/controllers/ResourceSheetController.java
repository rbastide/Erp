package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.entity.ResourceSheet;
import fr.iut_unilim.erp_back.service.ResourceSheetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/resourceSheet")
public class ResourceSheetController {

    private ResourceSheetService resourceSheetService;

    public ResourceSheetController(ResourceSheetService resourceSheetService) {
        this.resourceSheetService = resourceSheetService;
    }
}
