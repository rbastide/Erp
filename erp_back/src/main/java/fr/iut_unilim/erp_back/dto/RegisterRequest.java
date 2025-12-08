package fr.iut_unilim.erp_back.dto;

public class RegisterRequest {
    private String identifier;
    private String password;
    private String role;

    public String getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
