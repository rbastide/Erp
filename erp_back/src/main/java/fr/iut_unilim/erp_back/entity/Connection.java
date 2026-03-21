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

    @ManyToOne
    @JoinColumn(name = "roleID")
    private Role role;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "universityDepartmentID")
    private UniversityDepartment universityDepartment;

    @JoinColumn(name = "firstName")
    private String firstName;

    @JoinColumn(name = "lastName")
    private String lastName;

    public Connection() {
    }

    public Connection(String identifier, Role role, String email, UniversityDepartment universityDepartment) {
        this.identifier = identifier;
        this.role = role;
        this.email = email;
        this.universityDepartment = universityDepartment;
    }

    public Long getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UniversityDepartment getUniversityDepartment() {
        return universityDepartment;
    }

    public void setUniversityDepartment(UniversityDepartment universityDepartment) {
        this.universityDepartment = universityDepartment;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
