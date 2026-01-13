package fr.iut_unilim.erp_back.entity;

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

    @Column(name = "referencialTeacherID")
    private Long referencialTeacherID;

    @Column(name = "hourlyVolumeID")
    private Long hourlyVolumeID;

    @Column(name = "semester")
    private int semester;

    @Column(name = "year")
    private int year;

    @Column(name = "mainGoal")
    private String mainGoal;

    @Column(name = "linkedSAE")
    private Integer linkedSAE;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "lastModificationDate")
    private Date lastModificationDate;

    @ManyToMany(mappedBy = "resourceSheets")
    private List<Teacher> teachers;

    @ManyToMany(mappedBy = "resourceSheets")
    private List<Skill> skills;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "PedagologicalContentId")
    private List<PedagologicalContent> pedagologicalContent;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "improvementsIdeaID")
    private List<ImprovementIdeas> improvementIdeas;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentFeedbackID")
    private List<StudentsFeedbacks> studentsFeedbacks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "teachersFeedbackID")
    private List<PedagologicalTeachersFeedbacks> teachersFeedbacks;

    public ResourceSheet() {
    }

    public ResourceSheet(Long sheetsID, Long resourceID, Long referencialTeacherID, Long hourlyVolumeID, List<PedagologicalTeachersFeedbacks> teachersFeedbacks, List<StudentsFeedbacks> studentsFeedbacks, List<ImprovementIdeas> improvementIdeas, int semester, int year, String mainGoal, String content, int linkedSAE, Date creationDate, Date lastModificationDate, List<PedagologicalContent> pedagologicalContent) {
        this.sheetsID = sheetsID;
        this.resourceID = resourceID;
        this.referencialTeacherID = referencialTeacherID;
        this.hourlyVolumeID = hourlyVolumeID;
        this.teachersFeedbacks = teachersFeedbacks;
        this.studentsFeedbacks = studentsFeedbacks;
        this.improvementIdeas = improvementIdeas;
        this.semester = semester;
        this.year = year;
        this.mainGoal = mainGoal;
        this.linkedSAE = linkedSAE;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
        this.pedagologicalContent = pedagologicalContent;
    }

    public void setSheetsID(Long sheetsID) {
        this.sheetsID = sheetsID;
    }

    public void setResourceID(Long resourceID) {
        this.resourceID = resourceID;
    }

    public void setReferencialTeacherID(Long referencialTeacherID) {
        this.referencialTeacherID = referencialTeacherID;
    }

    public void setHourlyVolumeID(Long hourlyVolumeID) {
        this.hourlyVolumeID = hourlyVolumeID;
    }

    public void setTeachersFeedbacks(List<PedagologicalTeachersFeedbacks> teachersFeedbacks) {
        this.teachersFeedbacks = teachersFeedbacks;
    }

    public void setStudentsFeedbacks(List<StudentsFeedbacks> studentsFeedbacks) {
        this.studentsFeedbacks = studentsFeedbacks;
    }

    public void setImprovementIdeas(List<ImprovementIdeas> improvementIdeas) {
        this.improvementIdeas = improvementIdeas;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMainGoal(String mainGoal) {
        this.mainGoal = mainGoal;
    }

    public void setLinkedSAE(Integer linkedSAE) {
        this.linkedSAE = linkedSAE;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void setPedagologicalContent(List<PedagologicalContent> pedagologicalContent) {
        this.pedagologicalContent = pedagologicalContent;
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

    public List<PedagologicalTeachersFeedbacks> getTeachersFeedbacks() {
        return teachersFeedbacks;
    }

    public Long getReferencialTeacherID() {
        return referencialTeacherID;
    }

    public List<ImprovementIdeas> getImprovementIdeas() {
        return improvementIdeas;
    }

    public int getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    public String getMainGoal() {
        return mainGoal;
    }

    public Integer getLinkedSAE() {
        return linkedSAE;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public List<PedagologicalContent> getPedagologicalContent() {
        return pedagologicalContent;
    }




}



