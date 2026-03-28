package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "AvailableMenu")
public class AvailableMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuID")
    private Long id;

    @Column(name = "menuLabel")
    private String label;

    @Column(name = "permissionKey")
    private String permissionKey;

    @Column(name = "route")
    private String route;

    @Column(name = "iconID")
    private String iconId;

    public String getPermissionKey() {
        return permissionKey;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getIconId() {
        return iconId;
    }

    public String getRoute() {
        return route;
    }
}
