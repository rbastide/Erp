package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ImprovementIdeas")
public class ImprovementIdeas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "improvementsIdeaID")
    private Long improvementsIdeaID;

    @Column(name = "idea")
    private String idea;

    public ImprovementIdeas() {
    }

    public Long getImprovementsIdeaID(){ return this.improvementsIdeaID; }
    public String getIdea(){ return this.idea; }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public void setImprovementsIdeaID(Long improvementsIdeaID) {
        this.improvementsIdeaID = improvementsIdeaID;
    }
}
