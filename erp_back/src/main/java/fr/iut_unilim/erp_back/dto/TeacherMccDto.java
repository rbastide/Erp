package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.TeacherResource;

public record TeacherMccDto(Long teacherID, boolean isManager) {
    public TeacherMccDto(TeacherResource teacherResource) {
        this(
                teacherResource.getConnection().getId(),
                teacherResource.getIsManager()
        );
    }
}
