package fr.iut_unilim.erp_back.dto;

public class TeacherRequest {

    private Long teacherID;
    private String firstName;
    private String lastName;
    private Long userID;

    public Long getTeacherID() {
        return teacherID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getUserID() {
        return userID;
    }

    public void setTeacherID(Long teacherID) {
        this.teacherID = teacherID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
