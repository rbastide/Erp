package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Connection")
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private Long id;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "hashedPassword")
    private String hashedPassword;

    @Column(name = "role")
    private String role;

    @Column(name = "email")
    private String email;

    public Connection() {}

    public Connection(String identifier, String hashedPassword, String role, String email) {
        this.identifier = identifier;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getRole() {
        return role;
    }
    public String getEmail() {return email;}

    public void setPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setIdentifier(String identifier) { this.identifier = identifier; }
    public void setRole(String role) { this.role = role; }

    public void setEmail(String email) { this.email = email; }

}
