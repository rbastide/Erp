package fr.iut_unilim.erp_back.dto;

import java.util.Date;

public class UniversityDepartmentRequest {
    private Long univeristyDepartmentID;
    private String universityName;
    private String universityDepartmentName;
    private String city;
    private Date creationDate;

    public void setUniversityDepartmentID(Long universityDepartmentID) {
        this.univeristyDepartmentID = universityDepartmentID;
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

    public Long getUniversityDepartmentID() {
        return univeristyDepartmentID;
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
}
