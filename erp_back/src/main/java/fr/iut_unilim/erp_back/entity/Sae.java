package fr.iut_unilim.erp_back.entity;

import fr.iut_unilim.erp_back.tools.datastructures.SAE;
import jakarta.persistence.*;

@Entity
@Table(name = "SAE")
public class Sae {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SaeID;

    private String num;
    private String title;

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
}
