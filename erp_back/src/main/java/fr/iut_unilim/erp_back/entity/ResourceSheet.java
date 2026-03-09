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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "courseHoursID")
    private CourseHours courseHours;

    @Column(name = "creationDate")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
    private Date creationDate;

    @Column(name = "lastModificationDate")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
    private Date lastModificationDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "improvementIdeaID")
    private ImprovementIdea improvementIdeas;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "teacherFeedbackID")
    private TeacherFeedback teacherFeedbacks;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "studentFeedbackID")
    private StudentFeedback studentFeedbacks;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "resourceSheet")
    private List<EducationalContent> educationalContents;

    @Column(name = "isValidate")
    private boolean isValidate;

    @Column(name = "academicYearStart")
    private Integer academicYearStart;

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

    public void setEducationalContents(List<EducationalContent> educationalContentID) {
        this.educationalContents = educationalContentID;
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

    public ImprovementIdea getImprovementIdeas() {
        return improvementIdeas;
    }

    public void setImprovementIdeas(ImprovementIdea improvementIdeas) {
        this.improvementIdeas = improvementIdeas;
    }

    public TeacherFeedback getTeacherFeedbacks() {
        return teacherFeedbacks;
    }

    public void setTeacherFeedbacks(TeacherFeedback teacherFeedbacks) {
        this.teacherFeedbacks = teacherFeedbacks;
    }

    public StudentFeedback getStudentFeedbacks() {
        return studentFeedbacks;
    }

    public void setStudentFeedbacks(StudentFeedback studentFeedbacks) {
        this.studentFeedbacks = studentFeedbacks;
    }

    public List<EducationalContent> getEducationalContents() {
        return educationalContents;
    }

    public boolean isValidate() {
        return isValidate;
    }

    public void setValidate(boolean validate) {
        isValidate = validate;
    }

    public Integer getAcademicYearStart() {
        return academicYearStart;
    }

    public void setAcademicYearStart(Integer academicYearStart) {
        this.academicYearStart = academicYearStart;
    }
}
