package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.model.TeacherMccModel;

public record McccRequest(Long resourceID,
                          Float minCM,
                          Float minTD,
                          Float minTP,
                          Float minDS,
                          Float minDSTP,
                          Integer year,
                          Long[] saeIDs,
                          Long[] skillIDs,
                          TeacherMccModel[] teachers) {
}
