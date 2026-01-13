package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacherID")
    private Long teacherID;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @JoinColumn(name = "userID")
    private Long userID;

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

    public Teacher(String lastname, String firstname,Long userID) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.userID = userID;
    }

    public Long getTeacherID() {
        return teacherID;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public Long getuserID() {
        return userID;
    }

    public void setTeacherID(Long id) {
        this.teacherID = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setuserID(Long userID) {
        this.userID = userID;
    }
}