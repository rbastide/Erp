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

    public Resource(String num, String name) {
        this.num = num;
        this.name = name;
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
}
