package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.Sae;
import fr.iut_unilim.erp_back.entity.UniversityDepartment;

import java.util.Date;
import java.util.List;

public class ResourceSheetRequest {

    private Long sheetsID;
    private Long resourceID;
    private Long hourlyVolumeID;
    private List<String> teachersFeedbackID;
    private List<String> studentFeedbackID;
    private List<String> improvementsIdeaID;

    private List<String> pedagologicalContent;

    private UniversityDepartment universityDepartment;


    public Long getSheetsID() { return sheetsID; }
    public Long getResourceID() { return resourceID; }
    public Long getHourlyVolumeID() { return hourlyVolumeID; }
    public List<String> getTeachersFeedbackID() { return teachersFeedbackID; }
    public List<String> getStudentFeedbackID() { return studentFeedbackID; }
    public List<String> getImprovementsIdeaID() { return improvementsIdeaID; }
    public List<String> getPedagologicalContent() { return pedagologicalContent; }
    public UniversityDepartment getUniversityDepartment() { return universityDepartment; }

    public void setSheetsID(Long sheetsID) { this.sheetsID = sheetsID; }
    public void setResourceID(Long resourceID) { this.resourceID = resourceID; }
    public void setHourlyVolumeID(Long hourlyVolumeID) { this.hourlyVolumeID = hourlyVolumeID; }
    public void setTeachersFeedbackID(List<String> teachersFeedbackID) { this.teachersFeedbackID = teachersFeedbackID; }
    public void setStudentFeedbackID(List<String> studentFeedbackID) { this.studentFeedbackID = studentFeedbackID; }
    public void setImprovementsIdeaID(List<String> improvementsIdeaID) { this.improvementsIdeaID = improvementsIdeaID; }
    public void setPedagologicalContent(List<String> pedagologicalContent) { this.pedagologicalContent = pedagologicalContent; }
    public void setUniversityDepartment(UniversityDepartment universityDepartment) { this.universityDepartment = universityDepartment; }
}