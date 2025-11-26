package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "StudentsFeedback")
public class StudentsFeedbacks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "studentFeedbackID")
    private Long studentFeedbackID;

    @Column(name = "content")
    private String content;

    public StudentsFeedbacks() {
    }

    public StudentsFeedbacks(Long studentFeedbackID, String content) {
        this.studentFeedbackID = studentFeedbackID;
        this.content = content;
    }

    public Long teachersFeedbackID() { return studentFeedbackID; }
    public String content(){ return content; }
}
