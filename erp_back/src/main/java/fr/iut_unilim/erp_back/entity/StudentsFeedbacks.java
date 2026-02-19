package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "StudentsFeedback")
public class StudentsFeedbacks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "studentsFeedbackID")
    private Long studentFeedbackID;

    @Column(name = "studentsFeedbackContent")
    private String content;

    @ManyToOne
    @JoinColumn(name = "resourceSheetID")
    private ResourceSheet resourceSheet;

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
