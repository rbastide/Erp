package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.AutomaticMailConfigRequest;
import fr.iut_unilim.erp_back.dto.AutomaticMailConfigResponse;
import fr.iut_unilim.erp_back.entity.AutomaticMailConfig;
import fr.iut_unilim.erp_back.service.AutomaticMailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mail")
public class AutomaticMailController {

    private final AutomaticMailService automaticMailService;

    public AutomaticMailController(AutomaticMailService automaticMailService) {
        this.automaticMailService = automaticMailService;
    }

    @PostMapping("/automatic")
    public ResponseEntity<String> sendAutomaticMails() {
        automaticMailService.sendAutomaticMails();
        return ResponseEntity.ok("Mail envoyé (vérifie ta boîte mail)");
    }

    @GetMapping("/config")
    @PreAuthorize("@securityService.isLogin()")
    public ResponseEntity<List<AutomaticMailConfigResponse>> getConfigs() {
        List<AutomaticMailConfigResponse> responses = automaticMailService.getAllConfigs().stream()
                .map(config -> new AutomaticMailConfigResponse(
                        config.getId(),
                        config.getLabel(),
                        config.getDayOfMonth(),
                        config.getTime(),
                        config.isOneTime(),
                        config.getMonthsAsList()
                ))
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/config")
    @PreAuthorize("@securityService.isLogin()")
    public ResponseEntity<?> saveConfig(@RequestBody AutomaticMailConfigRequest request) {
        AutomaticMailConfig config = new AutomaticMailConfig();

        if (request.id() != null) {
            config.setId(request.id());
        }
        config.setLabel(request.label());
        config.setDayOfMonth(request.day());
        config.setTime(request.time());
        config.setOneTime(request.isOneTime());
        config.setMonthsFromList(request.months());

        automaticMailService.saveConfig(config);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/config/{id}")
    @PreAuthorize("@securityService.isLogin()")
    public ResponseEntity<?> deleteConfig(@PathVariable Long id) {
        automaticMailService.deleteConfig(id);
        return ResponseEntity.ok().build();
    }
}

