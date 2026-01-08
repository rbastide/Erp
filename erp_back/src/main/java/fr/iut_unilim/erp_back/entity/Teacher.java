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

    public Teacher(fr.iut_unilim.erp_back.tools.datastructures.Teacher teacher) {
        this.lastname = teacher.lastname();
        this.firstname = teacher.firstname();
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}