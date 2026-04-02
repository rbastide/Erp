package fr.iut_unilim.erp_back.dto;

public record EditRolePermissionRequest(long permissionRoleId, long permissionId, boolean permissionState) {
}
