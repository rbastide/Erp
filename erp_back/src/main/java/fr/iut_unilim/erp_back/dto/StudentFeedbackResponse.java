package fr.iut_unilim.erp_back.dto;

public class StudentFeedbackResponse {

    private Long studentFeedbackID;
    private String content;

    public StudentFeedbackResponse() {}

    public StudentFeedbackResponse(Long studentFeedbackID, String content) {}

    public Long getStudentFeedbackID() {
        return studentFeedbackID;
    }

    public void setStudentFeedbackID(Long studentFeedbackID) {
        this.studentFeedbackID = studentFeedbackID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content=content;
    }


}
