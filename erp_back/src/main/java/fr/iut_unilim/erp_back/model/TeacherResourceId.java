package fr.iut_unilim.erp_back.model;

import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Resource;

import java.io.Serializable;

public record TeacherResourceId(Connection connection, Resource resource) implements Serializable {
}
