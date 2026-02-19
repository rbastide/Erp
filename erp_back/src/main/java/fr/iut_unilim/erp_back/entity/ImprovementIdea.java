package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ImprovementIdea")
public class ImprovementIdea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "improvementIdeaID")
    private Long improvementsIdeaID;

    @Column(name = "ideaContent")
    private String ideaContent;

    public ImprovementIdea() {
    }

    public Long getImprovementsIdeaID(){ return this.improvementsIdeaID; }

    public String getIdeaContent() {
        return this.ideaContent;
    }

    public void setIdeaContent(String idea) {
        this.ideaContent = idea;
    }

    public void setImprovementsIdeaID(Long improvementsIdeaID) {
        this.improvementsIdeaID = improvementsIdeaID;
    }
}
