package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.UniversityDepartment;

public class ResourceDto {
    private Long resourceID;
    private String num;
    private String name;
    private int semester;
    private UniversityDepartment universityDepartment;

    public ResourceDto(Long resourceID, String num, String name, int semester, UniversityDepartment universityDepartment) {
        this.resourceID = resourceID;
        this.num = num;
        this.name = name;
        this.semester = semester;
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

    public void setSemester(int semester) {
        this.semester = semester;
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

    public int getSemester() {
        return semester;
    }

    public UniversityDepartment getUniversityDepartment() {
        return universityDepartment;
    }
}
