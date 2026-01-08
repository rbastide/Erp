package fr.iut_unilim.erp_back.dto;

public class AuthResponse {
    private String token;
    private String role;
    private String type = "Bearer";

    public void setToken(String token) {
        this.token = token;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }
}
