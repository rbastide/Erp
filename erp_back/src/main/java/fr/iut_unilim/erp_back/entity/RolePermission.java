package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

import java.util.BitSet;

@Entity
@Table(name = "RolePermission")
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permissionID")
    private Long permissionID;

    @ManyToOne
    @JoinColumn(name = "roleID")
    private Role role;

    @Column(name = "routePermission")
    private byte[] routePermission;

    public Long getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(Long permissionID) {
        this.permissionID = permissionID;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role resource) {
        this.role = resource;
    }

    public BitSet getBitSet() {
        return BitSet.valueOf(routePermission != null ? routePermission : new byte[32]);
    }

    public void setBitSet(BitSet bitSet) {
        this.routePermission = bitSet.toByteArray();
    }
}
