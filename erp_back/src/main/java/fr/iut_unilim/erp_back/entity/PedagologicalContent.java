package fr.iut_unilim.erp_back.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonBackReference
    private ResourceSheet ressourceSheetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classTypeId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    public void setPedagologicalContentId(Long pedagologicalContentId) {
        PedagologicalContentId = pedagologicalContentId;
    }

    public void setRessourceSheetId(ResourceSheet ressourceSheetId) {
        this.ressourceSheetId = ressourceSheetId;
    }

    public void setClassTypeId(ClassType classTypeId) {
        this.classTypeId = classTypeId;
    }

    public void setCourseNumber(Long courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
