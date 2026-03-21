package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TeacherResource")
public class TeacherResource {
    @Id
    @ManyToOne
    @JoinColumn(name = "connectionID")
    private Connection connection;

    @ManyToOne
    @JoinColumn(name = "resourceID")
    private Resource resource;

    @Column(name = "isManager")
    private boolean isManager;

    public TeacherResource() {
    }

    public TeacherResource(Connection connection, Resource resource) {
        this.connection = connection;
        this.resource = resource;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connectionID) {
        this.connection = connectionID;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resourceID) {
        this.resource = resourceID;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }
}