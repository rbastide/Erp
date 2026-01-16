package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.UniversityDepartment;

public class ResourceResponse {
    private Long resourceID;
    private String num;
    private String name;
    private int semestre;
    private UniversityDepartment universityDepartment;

    public ResourceResponse(Long resourceID, String num, String name, int semestre, UniversityDepartment universityDepartment) {
        this.resourceID = resourceID;
        this.num = num;
        this.name = name;
        this.semestre = semestre;
        this.universityDepartment = universityDepartment;
    }


    public void setResourceID(Long resourceID) {
        this.resourceID = resourceID;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public void setUniversityDepartment(UniversityDepartment universityDepartment) {
        this.universityDepartment = universityDepartment;
    }

    public Long getResourceID() {
        return resourceID;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public int getSemestre() {
        return semestre;
    }

    public UniversityDepartment getUniversityDepartment() {
        return universityDepartment;
    }
}
