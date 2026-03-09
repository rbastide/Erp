package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "StudentFeedback")
public class StudentFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentFeedbackID")
    private Long studentFeedbackID;

    @Column(name = "content")
    private String content;

    public StudentFeedback(String content) {
        this.content = content;
    }

    public StudentFeedback() {

    }

    public Long getStudentFeedbackID() {
        return studentFeedbackID;
    }

    public String getContent() {
        return content;
    }
}
