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

    public Long hourlyVolID() { return hourlyVolID; }
    public int nbHoursCM() { return nbHoursCM; }
    public int nbHoursDSTP() { return nbHoursDSTP; }
    public int nbHoursDS() { return nbHoursDS; }
    public int nbHoursTP() { return nbHoursTP; }
    public int nbHoursTD() { return nbHoursTD; }
}
