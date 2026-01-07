package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Resource")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "resourceID")
    private Long resourceID;

    @Column(name = "num")
    private String num;

    @Column(name = "name")
    private String name;

    @Column(name="semester")
    private int semester;

    public Resource() {
    }

    public Resource(Long resourceID, String num, String name, int semester) {
        this.resourceID = resourceID;
        this.num = num;
        this.name = name;
        this.semester = semester;
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
}
