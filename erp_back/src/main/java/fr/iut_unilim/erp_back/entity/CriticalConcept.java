package fr.iut_unilim.erp_back.entity;


import fr.iut_unilim.erp_back.model.CriticalConceptModel;
import jakarta.persistence.*;

@Entity
@Table(name = "CriticalConcept")
public class CriticalConcept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "criticalConceptID")
    private Long criticalConceptID;

    @Column(name = "criticalConceptNum")
    private int criticalConceptNum;

    @Column(name = "criticalConceptTitle")
    private String criticalConceptTitle;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "rankID")
    private Rank rankID;

    public CriticalConcept() {
    }

    public CriticalConcept(int criticalConceptNum, String criticalConceptTitle, Rank rankID) {
        this.criticalConceptNum = criticalConceptNum;
        this.criticalConceptTitle = criticalConceptTitle;
        this.rankID = rankID;
    }

    public CriticalConcept(CriticalConceptModel criticalConcept, Rank rankID) {
        this(criticalConcept.conceptNum(), criticalConcept.conceptTitle(), rankID);
    }

    public Long getCriticalConceptID() {
        return criticalConceptID;
    }

    public int getCriticalConceptNum() {
        return criticalConceptNum;
    }

    public String getCriticalConceptTitle() {
        return criticalConceptTitle;
    }

    public Rank getRankID() {
        return rankID;
    }

    public void setCriticalConceptID(Long learningID) {
        this.criticalConceptID = learningID;
    }

    public void setCriticalConceptNum(int learningNum) {
        this.criticalConceptNum = learningNum;
    }

    public void setCriticalConceptTitle(String learningTitle) {
        this.criticalConceptTitle = learningTitle;
    }

    public void setRankID(Rank rankID) {
        this.rankID = rankID;
    }
}
