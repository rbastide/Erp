package fr.iut_unilim.erp_back.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "CriticalLearnings")
public class CriticalLearning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "learningID")
    private int learningID;

    @Column(name = "learningNum")
    private int learningNum;

    @Column(name = "learningTitle")
    private String learningTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rankID")
    private Skill rankID;

    public CriticalLearning() {}


    public int getLearningID() {
        return learningID;
    }

    public int getLearningNum() {
        return learningNum;
    }

    public String getLearningTitle() {
        return learningTitle;
    }

    public Skill getRankID() {
        return rankID;
    }

    public void setLearningID(int learningID) {
        this.learningID = learningID;
    }

    public void setLearningNum(int learningNum) {
        this.learningNum = learningNum;
    }

    public void setLearningTitle(String learningTitle) {
        this.learningTitle = learningTitle;
    }

    public void setRankID(Skill rankID) {
        this.rankID = rankID;
    }
}
