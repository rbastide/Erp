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

    public HourlyVolume(Long hourlyVolID, int nbHoursCM, int nbHoursDS, int nbHoursDSTP, int nbHoursTP, int nbHoursTD) {
        this.hourlyVolID = hourlyVolID;
        this.nbHoursCM = nbHoursCM;
        this.nbHoursDSTP = nbHoursDSTP;
        this.nbHoursDS = nbHoursDS;
        this.nbHoursTP = nbHoursTP;
        this.nbHoursTD = nbHoursTD;
    }

    public Long getHourlyVolID() { return hourlyVolID; }
    public int getNbHoursCM() { return nbHoursCM; }
    public int getNbHoursDSTP() { return nbHoursDSTP; }
    public int getNbHoursDS() { return nbHoursDS; }
    public int getNbHoursTP() { return nbHoursTP; }
    public int getNbHoursTD() { return nbHoursTD; }
}
