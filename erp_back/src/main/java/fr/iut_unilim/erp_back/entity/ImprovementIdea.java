package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ImprovementIdea")
public class ImprovementIdea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "improvementIdeaID")
    private Long improvementIdeaID;

    @Column(name = "content")
    private String content;

    public ImprovementIdea(String content) {
        this.content = content;
    }

    public ImprovementIdea() {

    }

    public Long getImprovementIdeaID() {
        return improvementIdeaID;
    }

    public String getContent() {
        return content;
    }
}
