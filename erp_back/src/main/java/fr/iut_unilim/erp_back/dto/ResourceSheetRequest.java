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
    private Long linkedSaeID;

    private int semester;
    private int year;
    private String mainGoal;
    private String content;
    private Date creationDate;
    private Date lastModificationDate;

    private int hoursCM;
    private int hoursTD;
    private int hoursTP;
    private int hoursDS;
    private int hoursDSTP;

    private String teacherFeedbackContent;
    private String studentFeedbackContent;
    private String improvementIdeaContent;

    public Long getSheetsID() { return sheetsID; }
    public Long getResourceID() { return resourceID; }
    public Long getReferencialTeacherID() { return referencialTeacherID; }
    public Long getHourlyVolumeID() { return hourlyVolumeID; }
    public Long getTeachersFeedbackID() { return teachersFeedbackID; }
    public Long getStudentFeedbackID() { return studentFeedbackID; }
    public Long getImprovementsIdeaID() { return improvementsIdeaID; }
    public Long getLinkedSaeID() { return linkedSaeID; }

    public int getSemester() { return semester; }
    public int getYear() { return year; }
    public String getMainGoal() { return mainGoal; }
    public String getContent() { return content; }
    public Date getCreationDate() { return creationDate; }
    public Date getLastModificationDate() { return lastModificationDate; }

    public int getHoursCM() { return hoursCM; }
    public int getHoursTD() { return hoursTD; }
    public int getHoursTP() { return hoursTP; }
    public int getHoursDS() { return hoursDS; }
    public int getHoursDSTP() { return hoursDSTP; }

    public String getTeacherFeedbackContent() { return teacherFeedbackContent; }
    public String getStudentFeedbackContent() { return studentFeedbackContent; }
    public String getImprovementIdeaContent() { return improvementIdeaContent; }


    public void setSheetsID(Long sheetsID) { this.sheetsID = sheetsID; }
    public void setResourceID(Long resourceID) { this.resourceID = resourceID; }
    public void setReferencialTeacherID(Long referencialTeacherID) { this.referencialTeacherID = referencialTeacherID; }
    public void setHourlyVolumeID(Long hourlyVolumeID) { this.hourlyVolumeID = hourlyVolumeID; }
    public void setTeachersFeedbackID(Long teachersFeedbackID) { this.teachersFeedbackID = teachersFeedbackID; }
    public void setStudentFeedbackID(Long studentFeedbackID) { this.studentFeedbackID = studentFeedbackID; }
    public void setImprovementsIdeaID(Long improvementsIdeaID) { this.improvementsIdeaID = improvementsIdeaID; }
    public void setLinkedSaeID(Long linkedSaeID) { this.linkedSaeID = linkedSaeID; }

    public void setSemester(int semester) { this.semester = semester; }
    public void setYear(int year) { this.year = year; }
    public void setMainGoal(String mainGoal) { this.mainGoal = mainGoal; }
    public void setContent(String content) { this.content = content; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public void setLastModificationDate(Date lastModificationDate) { this.lastModificationDate = lastModificationDate; }

    public void setHoursCM(int hoursCM) { this.hoursCM = hoursCM; }
    public void setHoursTD(int hoursTD) { this.hoursTD = hoursTD; }
    public void setHoursTP(int hoursTP) { this.hoursTP = hoursTP; }
    public void setHoursDS(int hoursDS) { this.hoursDS = hoursDS; }
    public void setHoursDSTP(int hoursDSTP) { this.hoursDSTP = hoursDSTP; }

    public void setTeacherFeedbackContent(String teacherFeedbackContent) { this.teacherFeedbackContent = teacherFeedbackContent; }
    public void setStudentFeedbackContent(String studentFeedbackContent) { this.studentFeedbackContent = studentFeedbackContent; }
    public void setImprovementIdeaContent(String improvementIdeaContent) { this.improvementIdeaContent = improvementIdeaContent; }
}