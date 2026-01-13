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

    @Column(name = "hourlyVolumeID")
    private Long hourlyVolumeID;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "lastModificationDate")
    private Date lastModificationDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "sheetID")
    private List<PedagologicalTeachersFeedbacks> teachersFeedbacks;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "sheetID")
    private List<StudentsFeedbacks> studentsFeedbacks;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "sheetID")
    private List<ImprovementIdeas> improvementIdeas;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "ressourceSheetId")
    private List<PedagologicalContent> pedagologicalContentId;

    public ResourceSheet() {
    }

    public ResourceSheet(Long sheetsID, Long resourceID, Long hourlyVolumeID, List<PedagologicalTeachersFeedbacks> teachersFeedbacks, List<StudentsFeedbacks> studentsFeedbacks, List<ImprovementIdeas> improvementIdeas, Date creationDate, Date lastModificationDate, List<PedagologicalContent> pedagologicalContent) {
        this.sheetsID = sheetsID;
        this.resourceID = resourceID;
        this.hourlyVolumeID = hourlyVolumeID;
        this.teachersFeedbacks = teachersFeedbacks;
        this.studentsFeedbacks = studentsFeedbacks;
        this.improvementIdeas = improvementIdeas;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
        this.pedagologicalContentId = pedagologicalContent;
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

    public void setTeachersFeedbacks(List<PedagologicalTeachersFeedbacks> teachersFeedbacks) {
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

    public void setPedagologicalContentId(List<PedagologicalContent> pedagologicalContent) {
        this.pedagologicalContentId = pedagologicalContent;
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

    public List<ImprovementIdeas> getImprovementIdeas() {
        return improvementIdeas;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public List<PedagologicalContent> getPedagologicalContentId() {
        return pedagologicalContentId;
    }

}



