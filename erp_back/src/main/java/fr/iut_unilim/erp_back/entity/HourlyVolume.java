package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "HourlyVolume")
public class HourlyVolume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hourlyVolID")
    private Long hourlyVolID;

    @Column(name = "nbHoursCM")
    private Float nbHoursCM;

    @Column(name = "nbHoursDSTP")
    private Float nbHoursDSTP;

    @Column(name = "nbHoursDS")
    private Float nbHoursDS;

    @Column(name = "nbHoursTP")
    private Float nbHoursTP;

    @Column(name = "nbHoursTD")
    private Float nbHoursTD;

    public HourlyVolume() {
    }

    public HourlyVolume(Float nbHoursCM, Float nbHoursDS, Float nbHoursDSTP, Float nbHoursTP, Float nbHoursTD) {
        this.nbHoursCM = nbHoursCM;
        this.nbHoursDSTP = nbHoursDSTP;
        this.nbHoursDS = nbHoursDS;
        this.nbHoursTP = nbHoursTP;
        this.nbHoursTD = nbHoursTD;
    }

    public void setHourlyVolID(Long hourlyVolID) {
        this.hourlyVolID = hourlyVolID;
    }

    public void setNbHoursCM(Float nbHoursCM) {
        this.nbHoursCM = nbHoursCM;
    }

    public void setNbHoursDSTP(Float nbHoursDSTP) {
        this.nbHoursDSTP = nbHoursDSTP;
    }

    public void setNbHoursDS(Float nbHoursDS) {
        this.nbHoursDS = nbHoursDS;
    }

    public void setNbHoursTP(Float nbHoursTP) {
        this.nbHoursTP = nbHoursTP;
    }

    public void setNbHoursTD(Float nbHoursTD) {
        this.nbHoursTD = nbHoursTD;
    }

    public Long getHourlyVolID() { return hourlyVolID; }
    public Float getNbHoursCM() { return nbHoursCM; }
    public Float getNbHoursDSTP() { return nbHoursDSTP; }
    public Float getNbHoursDS() { return nbHoursDS; }
    public Float getNbHoursTP() { return nbHoursTP; }
    public Float getNbHoursTD() { return nbHoursTD; }
}