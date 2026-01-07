package fr.iut_unilim.erp_back.dto;

public record EditUserRequest(Long id, String identifier, String role, String newPassword) {
}
