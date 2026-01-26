package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.UniversityDepartment;

import java.util.List;

public class ResourceSheetRequest {

    private Long sheetsID;
    private Long resourceID;
    private HourlyVolumeDto hourlyVolume;
    private List<String> teachersFeedbackID;
    private List<String> studentFeedbackID;
    private List<String> improvementsIdeaID;

    private List<String> educationalContent;

    private UniversityDepartment universityDepartment;


    public Long getSheetsID() { return sheetsID; }
    public Long getResourceID() { return resourceID; }
    public List<String> getTeachersFeedbackID() { return teachersFeedbackID; }
    public List<String> getStudentFeedbackID() { return studentFeedbackID; }
    public List<String> getImprovementsIdeaID() { return improvementsIdeaID; }

    public List<String> getEducationalContent() {
        return educationalContent;
    }
    public UniversityDepartment getUniversityDepartment() { return universityDepartment; }

    public void setSheetsID(Long sheetsID) { this.sheetsID = sheetsID; }
    public void setResourceID(Long resourceID) { this.resourceID = resourceID; }
    public void setTeachersFeedbackID(List<String> teachersFeedbackID) { this.teachersFeedbackID = teachersFeedbackID; }
    public void setStudentFeedbackID(List<String> studentFeedbackID) { this.studentFeedbackID = studentFeedbackID; }
    public void setImprovementsIdeaID(List<String> improvementsIdeaID) { this.improvementsIdeaID = improvementsIdeaID; }

    public void setEducationalContent(List<String> educationalContent) {
        this.educationalContent = educationalContent;
    }
    public void setUniversityDepartment(UniversityDepartment universityDepartment) { this.universityDepartment = universityDepartment; }

    public HourlyVolumeDto getHourlyVolume() {
        return hourlyVolume;
    }

}