package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.model.LearningRankModel;
import fr.iut_unilim.erp_back.model.SaeModel;
import fr.iut_unilim.erp_back.model.TeacherModel;

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

    private List<SaeModel> saeCodes;

    private List<LearningRankModel> acsGrouped;

    private TeacherModel[] referents;

    private UniversityDepartment universityDepartment;

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

    public List<SaeModel> getSaeCodes() {
        return saeCodes;
    }

    public List<LearningRankModel> getAcsGrouped() {
        return acsGrouped;
    }

    public TeacherModel[] getReferents() {
        return referents;
    }

    public UniversityDepartment getUniversityDepartment() { return universityDepartment; }
}
