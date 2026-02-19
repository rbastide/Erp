package fr.iut_unilim.erp_back.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "EducationalContent")
public class EducationalContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "educationalContentId")
    private Long educationalContentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resourceSheetId")
    @JsonBackReference
    private ResourceSheet ressourceSheetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classTypeId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ClassType classTypeId;

    @Column(name = "courseNum")
    private Long courseNumber;

    @Column(name = "content")
    private String content;

    public Long getEducationalContentId() {
        return educationalContentId;
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

    public void setEducationalContentId(Long educationalContentId) {
        this.educationalContentId = educationalContentId;
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
