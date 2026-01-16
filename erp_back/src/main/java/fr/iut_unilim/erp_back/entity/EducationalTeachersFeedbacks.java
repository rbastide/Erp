package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "EducationalTeachersFeedbacks")
public class EducationalTeachersFeedbacks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "teachersFeedbackID")
    private Long teachersFeedbackID;

    @Column(name = "content")
    private String content;

    public EducationalTeachersFeedbacks() {
    }

    public EducationalTeachersFeedbacks(Long teachersFeedbackID, String content) {
        this.teachersFeedbackID = teachersFeedbackID;
        this.content = content;
    }

    public Long teachersFeedbackID() { return teachersFeedbackID; }
    public String getContent(){ return content; }

    public void setTeachersFeedbackID(Long teachersFeedbackID) {
        this.teachersFeedbackID = teachersFeedbackID;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
