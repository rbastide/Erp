package fr.iut_unilim.erp_back.dto;

public record UserResponse(Long id, String identifier, String role, String email, String lastName, String firstName) {

}
