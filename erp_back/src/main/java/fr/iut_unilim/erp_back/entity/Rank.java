package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "SkillRanks")
public class Rank {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "rankID")
    private int rankID;

    @Column(name = "rankNum")
    private int rankNum;

    @Column(name = "rankTitle")
    private String rankTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skillID")
    private Skill skillID;

    public Rank() {};


    public int getRankID() {
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

    public void setRankID(int rankID) {
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
