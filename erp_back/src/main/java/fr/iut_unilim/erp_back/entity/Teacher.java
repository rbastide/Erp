package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacherID")
    private Long id;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @ManyToMany
    @JoinTable(
            name = "ResourceSheetTeachers",
            joinColumns = @JoinColumn(name = "teacherID"),
            inverseJoinColumns = @JoinColumn(name = "sheetID")
    )
    private List<ResourceSheet> resourceSheets;

    public Teacher() {}

    public Teacher(String lastname, String firstname) {
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public Long getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }
}
