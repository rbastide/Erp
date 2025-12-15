package fr.iut_unilim.erp_back.dto;

import java.util.List;

public class RessourceSheetRequest {
    // Infos générales
    private String resourceCode; // Pour "RX.XX"
    private String resourceName; // Pour "Intitulé de l'UE"
    private String mainGoal;

    // Heures (Au lieu de hourlyVolumeID)
    private int hoursCM;
    private int hoursTD;
    private int hoursTP;
    private int hoursDS;
    private int hoursDSTP;

    // Liaisons (Codes ou IDs)
    private String saeCode; // Pour "SX.XX"
    private String competenceCode; // Pour "UE1.1"
    private int semester;
    private int year;

    public String getResourceCode() {
        return resourceCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getMainGoal() {
        return mainGoal;
    }

    public int getHoursCM() {
        return hoursCM;
    }

    public int getHoursTD() {
        return hoursTD;
    }

    public int getHoursTP() {
        return hoursTP;
    }

    public int getHoursDS() {
        return hoursDS;
    }

    public int getHoursDSTP() {
        return hoursDSTP;
    }

    public String getSaeCode() {
        return saeCode;
    }

    public String getCompetenceCode() {
        return competenceCode;
    }

    public int getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setMainGoal(String mainGoal) {
        this.mainGoal = mainGoal;
    }

    public void setHoursCM(int hoursCM) {
        this.hoursCM = hoursCM;
    }

    public void setHoursTD(int hoursTD) {
        this.hoursTD = hoursTD;
    }

    public void setHoursTP(int hoursTP) {
        this.hoursTP = hoursTP;
    }

    public void setHoursDS(int hoursDS) {
        this.hoursDS = hoursDS;
    }

    public void setHoursDSTP(int hoursDSTP) {
        this.hoursDSTP = hoursDSTP;
    }

    public void setSaeCode(String saeCode) {
        this.saeCode = saeCode;
    }

    public void setCompetenceCode(String competenceCode) {
        this.competenceCode = competenceCode;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setYear(int year) {
        this.year = year;
    }
}