package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CourseHours")
public class CourseHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseHoursID")
    private Long courseHoursID;

    @Column(name = "nbMinCM")
    private Float nbMinCM;

    @Column(name = "nbMinDSTP")
    private Float nbMinDSTP;

    @Column(name = "nbMinDS")
    private Float nbMinDS;

    @Column(name = "nbMinTP")
    private Float nbMinTP;

    @Column(name = "nbMinTD")
    private Float nbMinTD;

    public CourseHours() {
    }

    public CourseHours(Float nbMinCM, Float nbMinDS, Float nbMinDSTP, Float nbMinTP, Float nbMinTD) {
        this.nbMinCM = nbMinCM;
        this.nbMinDSTP = nbMinDSTP;
        this.nbMinDS = nbMinDS;
        this.nbMinTP = nbMinTP;
        this.nbMinTD = nbMinTD;
    }

    public void setCourseHoursID(Long courseHoursID) {
        this.courseHoursID = courseHoursID;
    }

    public void setNbMinCM(Float nbMinCM) {
        this.nbMinCM = nbMinCM;
    }

    public void setNbMinDSTP(Float nbMinDSTP) {
        this.nbMinDSTP = nbMinDSTP;
    }

    public void setNbMinDS(Float nbMinDS) {
        this.nbMinDS = nbMinDS;
    }

    public void setNbMinTP(Float nbMinTP) {
        this.nbMinTP = nbMinTP;
    }

    public void setNbMinTD(Float nbMinTD) {
        this.nbMinTD = nbMinTD;
    }

    public Long getCourseHoursID() {
        return courseHoursID;
    }
    public Float getNbMinCM() { return nbMinCM; }
    public Float getNbMinDSTP() { return nbMinDSTP; }
    public Float getNbMinDS() { return nbMinDS; }
    public Float getNbMinTP() { return nbMinTP; }
    public Float getNbMinTD() { return nbMinTD; }
}