package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SkillRanks")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "rankID")
    private Long rankID;

    @Column(name = "rankNum")
    private int rankNum;

    @Column(name = "rankTitle")
    private String rankTitle;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "skillID")
    private Skill skillID;

    public Rank() {
    }

    public Rank(int rankNum, String rankTitle, Skill skillID) {
        this.rankNum = rankNum;
        this.rankTitle = rankTitle;
        this.skillID = skillID;
    }

    public Long getRankID() {
        return rankID;
    }

    public int getRankNum() {
        return rankNum;
    }

    public String getRankTitle() {
        return rankTitle;
    }

    public Skill getSkillID() {
        return skillID;
    }

    public void setRankID(Long rankID) {
        this.rankID = rankID;
    }

    public void setRankNum(int rankNum) {
        this.rankNum = rankNum;
    }

    public void setRankTitle(String rankTitle) {
        this.rankTitle = rankTitle;
    }

    public void setSkillID(Skill skillID) {
        this.skillID = skillID;
    }
}
