package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PermissionDefinition")
public class PermissionDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permissionDefinitionID")
    private Long permissionDefinitionID;

    @Column(name = "permissionBitIndex")
    private int permissionDefinitionBitIndex;

    @Column(name = "permissionLabel")
    private String permissionLabel;

    @Column(name = "permissionKey")
    private String permissionKey;

    public PermissionDefinition() {}

    public Long getPermissionDefinitionID() {
        return permissionDefinitionID;
    }

    public void setPermissionDefinitionID(Long permissionDefinitionID) {
        this.permissionDefinitionID = permissionDefinitionID;
    }

    public int getPermissionDefinitionBitIndex() {
        return permissionDefinitionBitIndex;
    }

    public void setPermissionDefinitionBitIndex(int permissionDefinitionName) {
        this.permissionDefinitionBitIndex = permissionDefinitionName;
    }

    public String getPermissionLabel() {
        return permissionLabel;
    }

    public void setPermissionLabel(String permissionLabel) {
        this.permissionLabel = permissionLabel;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }
}
