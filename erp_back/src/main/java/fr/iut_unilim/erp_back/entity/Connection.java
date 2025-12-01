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

    public Connection() {}

    public Connection(String identifier, String hashedPassword, String role) {
        this.identifier = identifier;
        this.hashedPassword = hashedPassword;
        this.role = role;
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

    public void setPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
