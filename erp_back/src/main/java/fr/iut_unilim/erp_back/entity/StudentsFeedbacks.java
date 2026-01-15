package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "StudentFeedbacks")
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

    public Long studentsFeedbackID() { return studentFeedbackID; }
    public String getContent(){ return content; }



    public Long getStudentFeedbackID() {
        return studentFeedbackID;
    }

    public void setStudentFeedbackID(Long studentFeedbackID) {
        this.studentFeedbackID = studentFeedbackID;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
