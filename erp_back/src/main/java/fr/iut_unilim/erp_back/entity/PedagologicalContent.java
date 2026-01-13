package fr.iut_unilim.erp_back.entity;


import jakarta.persistence.*;

@Entity
@Table(name="PedagologicalContent")
public class PedagologicalContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PedagologicalContentId")
    private Long PedagologicalContentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ressourceSheetId")
    private ResourceSheet ressourceSheetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classTypeId")
    private ClassType classTypeId;

    @Column(name = "courseNumber")
    private Long courseNumber;

    @Column(name = "Content")
    private String content;

    public Long getPedagologicalContentId() {
        return PedagologicalContentId;
    }

    public ResourceSheet getRessourceSheetId() {
        return ressourceSheetId;
    }

    public ClassType getClassTypeId() {
        return classTypeId;
    }

    public Long getCourseNumber() {
        return courseNumber;
    }

    public String getContent() {
        return content;
    }
}
