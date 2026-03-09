package fr.iut_unilim.erp_back.entity;


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
    private ResourceSheet resourceSheet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classTypeId")
    private ClassType classType;

    @Column(name = "courseNum")
    private Long courseNumber;

    @Column(name = "content")
    private String content;

    public Long getEducationalContentId() {
        return educationalContentId;
    }

    public ResourceSheet getResourceSheet() {
        return resourceSheet;
    }

    public ClassType getClassType() {
        return classType;
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

    public void setResourceSheet(ResourceSheet ressourceSheetId) {
        this.resourceSheet = ressourceSheetId;
    }

    public void setClassType(ClassType classTypeId) {
        this.classType = classTypeId;
    }

    public void setCourseNumber(Long courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
