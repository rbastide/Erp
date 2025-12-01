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

    public Long improvementsIdeaID(){ return this.improvementsIdeaID; }
    public String idea(){ return this.idea; }
}
