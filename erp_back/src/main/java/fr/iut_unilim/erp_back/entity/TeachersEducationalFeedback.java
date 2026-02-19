package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TeachersEducationalFeedback")
public class TeachersEducationalFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "teachersFeedbackID")
    private Long teachersFeedbackID;

    @Column(name = "teachersFeedbackContent")
    private String content;

    @ManyToOne
    @JoinColumn(name = "resourceSheetID")
    private ResourceSheet resourceSheet;

    public TeachersEducationalFeedback() {
    }

    public TeachersEducationalFeedback(Long teachersFeedbackID, String content, ResourceSheet resourceSheet) {
        this.teachersFeedbackID = teachersFeedbackID;
        this.content = content;
        this.resourceSheet = resourceSheet;
    }

    public Long getTeachersFeedbackID() {
        return teachersFeedbackID;
    }

    public String getContent() {
        return content;
    }

    public void setTeachersFeedbackID(Long teachersFeedbackID) {
        this.teachersFeedbackID = teachersFeedbackID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ResourceSheet getResourceSheet() {
        return resourceSheet;
    }

    public void setResourceSheet(ResourceSheet resourceSheet) {
        this.resourceSheet = resourceSheet;
    }
}
