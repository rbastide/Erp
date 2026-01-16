package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ImprovementIdeas")
public class ImprovementIdeas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "improvementsIdeaID")
    private Long improvementsIdeaID;

    @Column(name = "ideaContent")
    private String ideaContent;

    public ImprovementIdeas() {
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
