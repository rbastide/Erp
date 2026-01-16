package fr.iut_unilim.erp_back.entity;


import fr.iut_unilim.erp_back.model.CriticalLearningModel;
import jakarta.persistence.*;

@Entity
@Table(name = "CriticalLearnings")
public class CriticalLearning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "criticalLearningID")
    private Long criticalLearningID;

    @Column(name = "criticalLearningNum")
    private int criticalLearningNum;

    @Column(name = "criticalLearningTitle")
    private String criticalLearningTitle;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "rankID")
    private Rank rankID;

    public CriticalLearning() {}

    public CriticalLearning(int criticalLearningNum, String criticalLearningTitle, Rank rankID) {
        this.criticalLearningNum = criticalLearningNum;
        this.criticalLearningTitle = criticalLearningTitle;
        this.rankID = rankID;
    }

    public CriticalLearning(CriticalLearningModel criticalLearning, Rank rankID) {
        this(criticalLearning.learningNum(), criticalLearning.learningTitle(), rankID);
    }

    public Long getCriticalLearningID() {
        return criticalLearningID;
    }

    public int getCriticalLearningNum() {
        return criticalLearningNum;
    }

    public String getCriticalLearningTitle() {
        return criticalLearningTitle;
    }

    public Rank getRankID() {
        return rankID;
    }

    public void setCriticalLearningID(Long learningID) {
        this.criticalLearningID = learningID;
    }

    public void setCriticalLearningNum(int learningNum) {
        this.criticalLearningNum = learningNum;
    }

    public void setCriticalLearningTitle(String learningTitle) {
        this.criticalLearningTitle = learningTitle;
    }

    public void setRankID(Rank rankID) {
        this.rankID = rankID;
    }
}
