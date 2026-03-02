package fr.iut_unilim.erp_back.dto;

public class UniversityDepartmentRequest {
    private String universityName;
    private String universityDepartmentName;
    private String city;

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public void setUniversityDepartmentName(String universityDepartmentName) {
        this.universityDepartmentName = universityDepartmentName;
    }

    public void setCity(String city) {
        this.city = city;
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
}
