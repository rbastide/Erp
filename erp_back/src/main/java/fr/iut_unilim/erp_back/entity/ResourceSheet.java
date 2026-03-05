package fr.iut_unilim.erp_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ResourceSheet")
public class ResourceSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resourceSheetID")
    private Long sheetID;

    @ManyToOne
    @JoinColumn(name = "resourceID")
    private Resource resource;

    @ManyToOne
    @JoinColumn(name = "courseHoursID")
    private CourseHours courseHours;

    @Column(name = "creationDate")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
    private Date creationDate;

    @Column(name = "lastModificationDate")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
    private Date lastModificationDate;

    @Column(name = "improvementIdeas")
    private String improvementIdeas;

    @Column(name = "teacherFeedbacks")
    private String teacherFeedbacks;

    @Column(name = "studentFeedbacks")
    private String studentFeedbacks;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "resourceSheet")
    private List<EducationalContent> educationalContentID;

    @Column(name = "isValidate")
    private boolean isValidate;

    public ResourceSheet() {
    }

    public void setSheetID(Long sheetsID) {
        this.sheetID = sheetsID;
    }

    public void setResource(Resource resourceID) {
        this.resource = resourceID;
    }

    public void setCourseHours(CourseHours courseHours) {
        this.courseHours = courseHours;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public void setEducationalContentID(List<EducationalContent> educationalContentID) {
        this.educationalContentID = educationalContentID;
    }

    public Long getSheetID() {
        return sheetID;
    }

    public Resource getResource() {
        return resource;
    }

    public CourseHours getCourseHours() {
        return courseHours;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public String getImprovementIdeas() {
        return improvementIdeas;
    }

    public void setImprovementIdeas(String improvementIdeas) {
        this.improvementIdeas = improvementIdeas;
    }

    public String getTeacherFeedbacks() {
        return teacherFeedbacks;
    }

    public void setTeacherFeedbacks(String teacherFeedbacks) {
        this.teacherFeedbacks = teacherFeedbacks;
    }

    public String getStudentFeedbacks() {
        return studentFeedbacks;
    }

    public void setStudentFeedbacks(String studentFeedbacks) {
        this.studentFeedbacks = studentFeedbacks;
    }

    public List<EducationalContent> getEducationalContentID() {
        return educationalContentID;
    }

    public boolean isValidate() {
        return isValidate;
    }

    public void setValidate(boolean validate) {
        isValidate = validate;
    }
}
