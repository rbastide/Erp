package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.service.ConnectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/connections")
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @GetMapping("/search")
    public List<Connection> search(@RequestParam String identifier) {
        return connectionService.findByIdentifier(identifier);
    }
}
