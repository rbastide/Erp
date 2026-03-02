package fr.iut_unilim.erp_back.dto;

import java.util.Map;

public record EditCreateRoleRequest(Long id, String roleName, Map<Long, Boolean> permissions) {
}
