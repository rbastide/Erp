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
    private int nbHoursCM;

    @Column(name = "nbHoursDSTP")
    private int nbHoursDSTP;

    @Column(name = "nbHoursDS")
    private int nbHoursDS;

    @Column(name = "nbHoursTP")
    private int nbHoursTP;

    @Column(name = "nbHoursTD")
    private int nbHoursTD;

    public HourlyVolume() {
    }

    public HourlyVolume(int nbHoursCM, int nbHoursDS, int nbHoursDSTP, int nbHoursTP, int nbHoursTD) {
        this.nbHoursCM = nbHoursCM;
        this.nbHoursDSTP = nbHoursDSTP;
        this.nbHoursDS = nbHoursDS;
        this.nbHoursTP = nbHoursTP;
        this.nbHoursTD = nbHoursTD;
    }

    public void setHourlyVolID(Long hourlyVolID) {
        this.hourlyVolID = hourlyVolID;
    }

    public void setNbHoursCM(int nbHoursCM) {
        this.nbHoursCM = nbHoursCM;
    }

    public void setNbHoursDSTP(int nbHoursDSTP) {
        this.nbHoursDSTP = nbHoursDSTP;
    }

    public void setNbHoursDS(int nbHoursDS) {
        this.nbHoursDS = nbHoursDS;
    }

    public void setNbHoursTP(int nbHoursTP) {
        this.nbHoursTP = nbHoursTP;
    }

    public void setNbHoursTD(int nbHoursTD) {
        this.nbHoursTD = nbHoursTD;
    }

    public Long hourlyVolID() { return hourlyVolID; }
    public int nbHoursCM() { return nbHoursCM; }
    public int nbHoursDSTP() { return nbHoursDSTP; }
    public int nbHoursDS() { return nbHoursDS; }
    public int nbHoursTP() { return nbHoursTP; }
    public int nbHoursTD() { return nbHoursTD; }
}