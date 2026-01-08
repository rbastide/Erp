package fr.iut_unilim.erp_back.dto;

import java.util.Date;

public class ResourceSheetRequest {

    private Long sheetsID;
    private Long resourceID;
    private Long referencialTeacherID;
    private Long hourlyVolumeID;
    private Long teachersFeedbackID;
    private Long studentFeedbackID;
    private Long improvementsIdeaID;
    private int semester;
    private int year;
    private String mainGoal;
    private String content;
    private Long linkedSae;
    private Date creationDate;
    private Date lastModificationDate;

    public Long getSheetsID() {
        return sheetsID;
    }

    public Long getResourceID() {
        return resourceID;
    }

    public Long getReferencialTeacherID() {
        return referencialTeacherID;
    }

    public Long getHourlyVolumeID() {
        return hourlyVolumeID;
    }

    public Long getTeachersFeedbackID() {
        return teachersFeedbackID;
    }

    public Long getStudentFeedbackID() {
        return studentFeedbackID;
    }

    public Long getImprovementsIdeaID() {
        return improvementsIdeaID;
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

    public String getContent() {
        return content;
    }

    public Long getLinkedSaeID() {
        return linkedSae;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
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

    public void setTeachersFeedbackID(Long teachersFeedbackID) {
        this.teachersFeedbackID = teachersFeedbackID;
    }

    public void setStudentFeedbackID(Long studentFeedbackID) {
        this.studentFeedbackID = studentFeedbackID;
    }

    public void setImprovementsIdeaID(Long improvementsIdeaID) {
        this.improvementsIdeaID = improvementsIdeaID;
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

    public void setContent(String content) {
        this.content = content;
    }

    public void setLinkedSae(Long linkedSae) {
        this.linkedSae = linkedSae;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }
}