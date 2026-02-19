package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Resource")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "resourceID")
    private Long resourceID;

    @Column(name = "resourceNum")
    private String num;

    @Column(name = "resourceName")
    private String name;

    @Column(name="semester")
    private int semester;

    @ManyToOne
    @JoinColumn(name = "universityDepartmentID")
    private UniversityDepartment universityDepartment;

    public Resource() {
    }

    public Resource(Long resourceID, String num, String name, int semester,UniversityDepartment universityDepartment) {
        this.resourceID = resourceID;
        this.num = num;
        this.name = name;
        this.semester = semester;
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

    public int getSemester() {return semester;}

    public void setName(String name) {
        this.name = name;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setResourceID(Long resourceID) {
        this.resourceID = resourceID;
    }

    public UniversityDepartment getUniversityDepartment() {
        return universityDepartment;
    }

    public void setUniversityDepartment(UniversityDepartment universityDepartmentId) {
        this.universityDepartment = universityDepartmentId;
    }
}
