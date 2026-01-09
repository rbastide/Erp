package fr.iut_unilim.erp_back.dto;

public class RegisterRequest {
    private String identifier;
    private String password;
    private String role;
    private String email;

    public String getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {return email;}

    public void setRole(String role) {
        this.role = role;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {this.email = email;}
}
