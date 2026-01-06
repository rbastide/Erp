package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.tools.datastructures.LearningRank;
import fr.iut_unilim.erp_back.tools.datastructures.Teacher;

import java.util.List;

public class McccResponse {
    private String resourceCode;

    private int hoursCM;
    private int hoursTD;
    private int hoursTP;
    private int hoursDS;
    private int hoursDSTP;
    private int hoursTotal;

    private String creationDate;
    private String editDate;

    private String[] saeCodes;
    private String[] niveaux;
    private String[] ue;
    private String[] acs;

    private List<LearningRank> acsGrouped;

    private Teacher[] referents;

    public String getResourceCode() {
        return resourceCode;
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

    public int getHoursTotal() {
        return hoursTotal;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getEditDate() {
        return editDate;
    }

    public String[] getSaeCodes() {
        return saeCodes;
    }

    public String[] getNiveaux() {
        return niveaux;
    }

    public String[] getUe() {
        return ue;
    }

    public String[] getAcs() {
        return acs;
    }

    public List<LearningRank> getAcsGrouped() {
        return acsGrouped;
    }

    public Teacher[] getReferents() {
        return referents;
    }
}
