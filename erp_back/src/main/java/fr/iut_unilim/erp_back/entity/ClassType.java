package fr.iut_unilim.erp_back.entity;


import jakarta.persistence.*;

@Entity
@Table(name="ClassType")
public class ClassType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="classTypeId")
    private Long classTypeId;

    @Column(name = "classType")
    private String classType;

    public ClassType() {}

    public ClassType(Long classTypeId, String classType) {
        this.classTypeId = classTypeId;
        this.classType = classType;
    }

    public ClassType(String classType) {
        this.classType = classType;
    }

    public Long getClassTypeId() {
        return classTypeId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassTypeId(Long classTypeId) {
        this.classTypeId = classTypeId;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}
