package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.service.ResourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public List<Resource> getAllResources(){
        return resourceService.getAllResources();
    }

    @GetMapping("/search")
    public List<Resource> search(@RequestParam String name){
        return resourceService.findByName(name);
    }
}
