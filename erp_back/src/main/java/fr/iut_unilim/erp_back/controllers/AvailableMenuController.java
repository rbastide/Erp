package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.dto.MenuResponse;
import fr.iut_unilim.erp_back.service.AvailableMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class AvailableMenuController {
    private final AvailableMenuService availableMenuService;

    public AvailableMenuController(AvailableMenuService availableMenuService) {
        this.availableMenuService = availableMenuService;
    }

    @PreAuthorize("@securityService.isLogin()")
    @GetMapping("")
    public ResponseEntity<?> getMenus(Authentication authentication) {
        String username = authentication.getName();
        List<MenuResponse> availableMenus = availableMenuService.getAvailableMenusFromUser(username);

        return ResponseEntity.ok(availableMenus);
    }
}
