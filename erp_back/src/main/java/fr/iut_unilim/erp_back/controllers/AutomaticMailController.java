package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.service.AutomaticMailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

