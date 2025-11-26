package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "ResourceSheetSkills",
            joinColumns = @JoinColumn(name = "skillID"),
            inverseJoinColumns = @JoinColumn(name = "sheetID")
    )
    private List<ResourceSheet> resourceSheets;

    public Skill() {}

    public Skill(String skillName, String skillLearning) {
        this.skillName = skillName;
        this.skillLearning = skillLearning;
    }

    public Long getSkillID() {
        return skillID;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getSkillLearning() {
        return skillLearning;
    }
}
