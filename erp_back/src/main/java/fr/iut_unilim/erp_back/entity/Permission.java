package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

import java.util.BitSet;

@Entity
@Table(name = "Permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permissionID")
    private Long permissionID;

    @ManyToOne
    @JoinColumn(name = "roleID")
    private Role resource;

    @Column(name = "routePermission")
    private byte[] routePermission;

    public Long getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(Long permissionID) {
        this.permissionID = permissionID;
    }

    public Role getResource() {
        return resource;
    }

    public void setResource(Role resource) {
        this.resource = resource;
    }

    public BitSet getBitSet() {
        return BitSet.valueOf(routePermission != null ? routePermission : new byte[32]);
    }

    public void setBitSet(BitSet bitSet) {
        this.routePermission = bitSet.toByteArray();
    }
}
