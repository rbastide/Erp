package fr.iut_unilim.erp_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ResourceSheets")
public class ResourceSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sheetsID")
    private Long sheetsID;

    @Column(name = "resourceID")
    private Long resourceID;

    @Column(name = "hourlyVolumeID")
    private Long hourlyVolumeID;

    @Column(name = "creationDate")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
    private Date creationDate;

    @Column(name = "lastModificationDate")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Paris")
    private Date lastModificationDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "sheetID")
    @JsonManagedReference
    private List<TeachersEducationalFeedback> teachersFeedbacks;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "sheetID")
    @JsonManagedReference
    private List<StudentsFeedbacks> studentsFeedbacks;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "sheetID")
    @JsonManagedReference
    private List<ImprovementIdeas> improvementIdeas;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "resourceSheetId")
    @JsonManagedReference
    private List<EducationalContent> educationalContentID;

    @ManyToOne
    @JoinColumn(name = "universityDepartmentID")
    private UniversityDepartment universityDepartment;

    public ResourceSheet() {
    }

    public ResourceSheet(Long sheetsID, Long resourceID, Long hourlyVolumeID, List<TeachersEducationalFeedback> teachersFeedbacks, List<StudentsFeedbacks> studentsFeedbacks, List<ImprovementIdeas> improvementIdeas, Date creationDate, Date lastModificationDate, List<EducationalContent> educationalContentsID, UniversityDepartment universityDepartment) {
        this.sheetsID = sheetsID;
        this.resourceID = resourceID;
        this.hourlyVolumeID = hourlyVolumeID;
        this.teachersFeedbacks = teachersFeedbacks;
        this.studentsFeedbacks = studentsFeedbacks;
        this.improvementIdeas = improvementIdeas;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
        this.educationalContentID = educationalContentsID;
        this.universityDepartment = universityDepartment;
    }

    public void setSheetsID(Long sheetsID) {
        this.sheetsID = sheetsID;
    }

    public void setResourceID(Long resourceID) {
        this.resourceID = resourceID;
    }

    public void setHourlyVolumeID(Long hourlyVolumeID) {
        this.hourlyVolumeID = hourlyVolumeID;
    }

    public void setTeachersFeedbacks(List<TeachersEducationalFeedback> teachersFeedbacks) {
        this.teachersFeedbacks = teachersFeedbacks;
    }

    public void setStudentsFeedbacks(List<StudentsFeedbacks> studentsFeedbacks) {
        this.studentsFeedbacks = studentsFeedbacks;
    }

    public void setImprovementIdeas(List<ImprovementIdeas> improvementIdeas) {
        this.improvementIdeas = improvementIdeas;
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

    public Long getSheetsID() {
        return sheetsID;
    }

    public Long getResourceID() {
        return resourceID;
    }

    public Long getHourlyVolumeID() {
        return hourlyVolumeID;
    }

    public List<StudentsFeedbacks> getStudentsFeedbacks() {
        return studentsFeedbacks;
    }

    public List<TeachersEducationalFeedback> getTeachersFeedbacks() {
        return teachersFeedbacks;
    }

    public List<ImprovementIdeas> getImprovementIdeas() {
        return improvementIdeas;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public List<EducationalContent> getEducationalContentID() {
        return educationalContentID;
    }

    public UniversityDepartment getUniversityDepartment() {
        return universityDepartment;
    }

    public void setUniversityDepartment(UniversityDepartment universityDepartment) {
        this.universityDepartment = universityDepartment;
    }
}
