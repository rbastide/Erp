package fr.iut_unilim.erp_back.entity;

import fr.iut_unilim.erp_back.dto.TeacherMccDto;

import java.util.List;

public record McccResponse(Long resourceID,
                           String resourceCode,
                           Float minCM,
                           Float minTD,
                           Float minTP,
                           Float minDS,
                           Float minDSTP,
                           Integer year,
                           List<Long> saeIDs,
                           List<Long> skillIDs,
                           List<TeacherMccDto> teachers,
                           String creationDate,
                           String editDate) {
}
