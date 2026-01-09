package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.service.AutomaticMailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class MailTestController {

    private final AutomaticMailService automaticMailService;

    public MailTestController(AutomaticMailService automaticMailService) {
        this.automaticMailService = automaticMailService;
    }

    @PostMapping("/test")
    public ResponseEntity<String> sendTestMail() {
        automaticMailService.sendAutomaticMail(
                "nathan.billaud@etu.unilim.fr",
                "Test de mail",
                "Si tu reçois ça, c'est que la configuration fonctionne."
        );
        return ResponseEntity.ok("Mail envoyé (vérifie ta boîte mail)");
    }
}

