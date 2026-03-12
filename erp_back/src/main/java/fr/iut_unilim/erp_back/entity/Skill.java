package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skillID")
    private Long skillID;

    @Column(name = "skillNum")
    private int skillNum;

    @Column(name = "skillName")
    private String skillName;

    @ManyToOne
    @JoinColumn(name = "universityDepartmentID")
    private UniversityDepartment universityDepartment;

    @OneToMany(mappedBy = "skillID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rank> ranks = new ArrayList<>();

    public Skill() {}

    public Skill(String skillName, int skillNum){
        this.skillName = skillName;
        this.skillNum = skillNum;
    }

    public Skill(String skillName, int skillNum, UniversityDepartment universityDepartment) {
        this.skillName = skillName;
        this.skillNum = skillNum;
        this.universityDepartment = universityDepartment;
    }

    public Long getSkillID() { return skillID; }
    public int getSkillNum() { return skillNum; }
    public String getSkillName() { return skillName; }
    public int getSkillLearning() { return skillNum; }
    public UniversityDepartment getUniversityDepartment() { return universityDepartment; }
    public List<Rank> getRanks() { return ranks; } // <-- Ajout du Getter

    public void setSkillID(Long skillID) { this.skillID = skillID; }
    public void setSkillNum(int skillNum) { this.skillNum = skillNum; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    public void setUniversityDepartment(UniversityDepartment universityDepartment) { this.universityDepartment = universityDepartment; }
    public void setRanks(List<Rank> ranks) { this.ranks = ranks; } // <-- Ajout du Setter
}