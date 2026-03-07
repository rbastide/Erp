package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TeacherFeedback")
public class TeacherFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacherFeedbackID")
    private Long teacherFeedbackID;

    @Column(name = "content")
    private String content;

    public TeacherFeedback(String content) {
        this.content = content;
    }

    public TeacherFeedback() {

    }

    public Long getTeacherFeedbackID() {
        return teacherFeedbackID;
    }

    public String getContent() {
        return content;
    }
}
