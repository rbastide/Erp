package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.UniversityDepartment;
import fr.iut_unilim.erp_back.model.LearningRankModel;
import fr.iut_unilim.erp_back.model.SaeModel;
import fr.iut_unilim.erp_back.model.TeacherModel;

import java.util.List;

public class McccRequest {
    private Long resourceID;

    private Float minCM;
    private Float minTD;
    private Float minTP;
    private Float minDS;
    private Float hoursDSTP;
    public Integer year;

    private String creationDate;
    private String editDate;

    private List<SaeModel> saeCodes;

    private List<LearningRankModel> acsGrouped;

    private TeacherModel[] referents;

    private UniversityDepartment universityDepartment;

    public Long getResourceID() {
        return resourceID;
    }

    public Float getMinCM() {
        return minCM;
    }

    public Float getMinTD() {
        return minTD;
    }

    public Float getMinTP() {
        return minTP;
    }

    public Float getMinDS() {
        return minDS;
    }

    public Float getHoursDSTP() {
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

    public Integer getYear() { return year; }
}
