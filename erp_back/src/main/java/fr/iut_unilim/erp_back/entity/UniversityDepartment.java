package fr.iut_unilim.erp_back.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="UniversityDepartment")
public class UniversityDepartment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="universityDepartmentID")
    private Long universityDepartmentID;

    @Column(name="universityName")
    private String universityName;

    @Column(name="universityDepartmentName")
    private String universityDepartmentName;

    @Column(name="city")
    private String city;

    @Column(name = "creationDate")
    private Date creationDate;


    public UniversityDepartment(Long universityDepartmentID, String universityDepartmentName, Date creationDate, String city, String universityName) {
        this.universityDepartmentID = universityDepartmentID;
        this.universityDepartmentName = universityDepartmentName;
        this.creationDate = creationDate;
        this.city = city;
        this.universityName = universityName;
    }

    public UniversityDepartment() {
    }

    public Long getUniversityDepartmentID() {
        return universityDepartmentID;
    }

    public String getUniversityName() {
        return universityName;
    }

    public String getUniversityDepartmentName() {
        return universityDepartmentName;
    }

    public String getCity() {
        return city;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setUniversityDepartmentID(Long universityDepartmentID) {
        this.universityDepartmentID = universityDepartmentID;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public void setUniversityDepartmentName(String universityDepartmentName) {
        this.universityDepartmentName = universityDepartmentName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
