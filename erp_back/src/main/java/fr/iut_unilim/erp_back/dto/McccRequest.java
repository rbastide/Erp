package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.tools.datastructures.LearningRank;
import fr.iut_unilim.erp_back.tools.datastructures.SAE;
import fr.iut_unilim.erp_back.tools.datastructures.Teacher;

import java.util.List;

public class McccRequest {
    private Long resourceID;

    private int hoursCM;
    private int hoursTD;
    private int hoursTP;
    private int hoursDS;
    private int hoursDSTP;

    private String creationDate;
    private String editDate;

    private List<SAE> saeCodes;

    private List<LearningRank> acsGrouped;

    private Teacher[] referents;

    public Long getResourceID() {
        return resourceID;
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

    public String getCreationDate() {
        return creationDate;
    }

    public String getEditDate() {
        return editDate;
    }

    public List<SAE> getSaeCodes() {
        return saeCodes;
    }

    public List<LearningRank> getAcsGrouped() {
        return acsGrouped;
    }

    public Teacher[] getReferents() {
        return referents;
    }
}
