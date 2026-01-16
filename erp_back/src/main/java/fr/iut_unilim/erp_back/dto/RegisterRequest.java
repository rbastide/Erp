package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.UniversityDepartment;

public class RegisterRequest {
    private String identifier;
    private String password;
    private String role;
    private String email;
    private String firstname;
    private String lastname;
    private UniversityDepartment universityDepartment;

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
    public String getFirstname() {return firstname;}
    public String getLastname() {return lastname;}
    public UniversityDepartment getUniversityDepartment() {
        return universityDepartment;
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

    public void setEmail(String email) {this.email = email;}

    public void setFirstname(String firstname) {this.firstname = firstname;}

    public void setLastname(String lastname) {this.lastname = lastname;}
}
