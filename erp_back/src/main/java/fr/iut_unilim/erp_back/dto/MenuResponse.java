package fr.iut_unilim.erp_back.dto;

import java.util.List;

public record MenuResponse(long id, String label, String route, String iconId, List<MenuResponse> children) {
}
