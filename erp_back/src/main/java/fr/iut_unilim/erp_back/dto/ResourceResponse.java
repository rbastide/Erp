package fr.iut_unilim.erp_back.dto;

public class ResourceResponse {
    private Long resourceID;
    private String num;
    private String name;
    private int semestre;

    public ResourceResponse(Long resourceID, String num, String name, int semestre) {
        this.resourceID = resourceID;
        this.num = num;
        this.name = name;
        this.semestre = semestre;
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
}
