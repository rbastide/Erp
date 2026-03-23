package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.entity.Connection;

public record TeacherResponse(Long id, String lastname, String firstname) {
    public TeacherResponse(Connection connection) {
        this(connection.getId(), connection.getLastName(), connection.getFirstName());
    }
}
