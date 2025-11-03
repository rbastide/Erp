package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

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

    public Teacher() {
    }

    public Teacher(String lastname, String firstname) {
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public Long id() {
        return id;
    }

    public String lastname() {
        return lastname;
    }

    public String firstname() {
        return firstname;
    }
}
