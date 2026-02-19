package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

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

    public Long getSkillID() {
        return skillID;
    }

    public int getSkillNum() {
        return skillNum;
    }

    public String getSkillName() {
        return skillName;
    }

    public int getSkillLearning() {
        return skillNum;
    }

    public void setSkillID(Long skillID) {
        this.skillID = skillID;
    }

    public void setSkillNum(int skillNum) {
        this.skillNum = skillNum;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public UniversityDepartment getUniversityDepartment() {
        return universityDepartment;
    }

    public void setUniversityDepartment(UniversityDepartment universityDepartment) {
        this.universityDepartment = universityDepartment;
    }
}
