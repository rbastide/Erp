package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PedagologicalTeachersFeedback")
public class PedagologicalTeachersFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "teachersFeedbackID")
    private Long teachersFeedbackID;

    @Column(name = "content")
    private String content;

    public PedagologicalTeachersFeedback() {
    }

    public PedagologicalTeachersFeedback(Long teachersFeedbackID, String content) {
        this.teachersFeedbackID = teachersFeedbackID;
        this.content = content;
    }

    public Long teachersFeedbackID() { return teachersFeedbackID; }
    public String content(){ return content; }
}
