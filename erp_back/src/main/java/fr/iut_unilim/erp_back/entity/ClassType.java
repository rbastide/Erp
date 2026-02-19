package fr.iut_unilim.erp_back.entity;


import jakarta.persistence.*;

@Entity
@Table(name="ClassType")
public class ClassType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="classTypeId")
    private Long classTypeId;

    @Column(name = "classTypeName")
    private String classTypeName;

    public ClassType() {}

    public ClassType(Long classTypeId, String classTypeName) {
        this.classTypeId = classTypeId;
        this.classTypeName = classTypeName;
    }

    public ClassType(String classTypeName) {
        this.classTypeName = classTypeName;
    }

    public Long getClassTypeId() {
        return classTypeId;
    }

    public String getClassTypeName() {
        return classTypeName;
    }

    public void setClassTypeId(Long classTypeId) {
        this.classTypeId = classTypeId;
    }

    public void setClassTypeName(String classType) {
        this.classTypeName = classType;
    }
}
