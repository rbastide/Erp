package fr.iut_unilim.erp_back.dto;

import java.util.Map;

public record RolePermissionResponse(long id, RoleResponse role, Map<Long, Boolean> permissions) {
}
