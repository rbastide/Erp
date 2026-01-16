package fr.iut_unilim.erp_back.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "CriticalLearnings")
public class CriticalLearning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "learningID")
    private Long learningID;

    @Column(name = "learningNum")
    private int learningNum;

    @Column(name = "learningTitle")
    private String learningTitle;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "rankID")
    private Rank rankID;

    public CriticalLearning() {}

    public CriticalLearning(int learningNum, String learningTitle, Rank rankID) {
        this.learningNum = learningNum;
        this.learningTitle = learningTitle;
        this.rankID = rankID;
    }

    public CriticalLearning(fr.iut_unilim.erp_back.tools.datastructures.CriticalLearning criticalLearning, Rank rankID) {
        this(criticalLearning.learningNum(), criticalLearning.learningTitle(), rankID);
    }

    public Long getLearningID() {
        return learningID;
    }

    public int getLearningNum() {
        return learningNum;
    }

    public String getLearningTitle() {
        return learningTitle;
    }

    public Rank getRankID() {
        return rankID;
    }

    public void setLearningID(Long learningID) {
        this.learningID = learningID;
    }

    public void setLearningNum(int learningNum) {
        this.learningNum = learningNum;
    }

    public void setLearningTitle(String learningTitle) {
        this.learningTitle = learningTitle;
    }

    public void setRankID(Rank rankID) {
        this.rankID = rankID;
    }
}
