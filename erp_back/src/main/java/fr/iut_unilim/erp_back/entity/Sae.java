package fr.iut_unilim.erp_back.entity;

import fr.iut_unilim.erp_back.tools.datastructures.SAE;
import jakarta.persistence.*;

@Entity
@Table(name = "SAE")
public class Sae {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="saeID")
    private Long SaeID;

    @Column(name = "num")
    private String num;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "universityDepartmentID")
    private UniversityDepartment universityDepartment;

    public Sae(Long saeID, String num, String title) {
        SaeID = saeID;
        this.num = num;
        this.title = title;
    }

    public Sae() {
    }

    public Sae(SAE sae) {
        this.num = sae.saeCode();
        this.title = sae.saeName();
    }

    public Long getSaeID() {
        return SaeID;
    }

    public void setSaeID(Long saeID) {
        SaeID = saeID;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UniversityDepartment getUniversityDepartment() {
        return universityDepartment;
    }

    public void setUniversityDepartment(UniversityDepartment universityDepartment) {
        this.universityDepartment = universityDepartment;
    }
}
