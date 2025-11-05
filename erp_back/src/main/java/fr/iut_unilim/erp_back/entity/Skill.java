package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "skillID")
    private Long skillID;

    @Column(name = "skillName")
    private String skillName;

    @Column(name = "skillLearning")
    private String skillLearning;

    public Skill() {}

    public Skill(String skillName, String skillLearning) {
        this.skillName = skillName;
        this.skillLearning = skillLearning;
    }

    public Long skillID() {
        return skillID;
    }
}
