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

    @Column(name = "isAdmin")
    private boolean isAdmin;

    public Connection() {}

    public Connection(String identifier, String hashedPassword, boolean isAdmin) {
        this.identifier = identifier;
        this.hashedPassword = hashedPassword;
        this.isAdmin = isAdmin;
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

    public boolean isAdmin() {
        return isAdmin;
    }
}
