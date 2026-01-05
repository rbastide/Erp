package fr.iut_unilim.erp_back.entity;

import fr.iut_unilim.erp_back.tools.datastructures.McccId;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "MCCC")
public class Mccc {
    @Id
    private McccId mcccId;

    @ManyToOne
    @JoinColumn(name = "hourlyVolID")
    private HourlyVolume hourlyVolId;

    @ManyToOne
    @JoinColumn(name = "resourceID")
    private Resource resourceId;

    @ManyToMany
    @JoinTable(
            name = "McccSaes",
            joinColumns = @JoinColumn(name = "mcccSaesID"),
            inverseJoinColumns = @JoinColumn(name = "saeID")
    )
    private Set<Sae> saeId;

    @ManyToOne
    @JoinColumn(name = "skillID")
    private Skill skillId;

    @ManyToMany
    @JoinTable(
            name = "MCCCTeachers",
            joinColumns = @JoinColumn(name = "referencialTeacher"),
            inverseJoinColumns = @JoinColumn(name = "teacherID")
    )
    private Set<Teacher> referencialTeacherId;

    private Date creationDate;
    private Date lastModificationDate;

    public Mccc(McccId mcccId, HourlyVolume hourlyVolId, Resource resourceId, Set<Sae> saeId, Skill skillId, Set<Teacher> referencialTeacherId) {
        this.mcccId = mcccId;
        this.hourlyVolId = hourlyVolId;
        this.resourceId = resourceId;
        this.saeId = saeId;
        this.skillId = skillId;
        this.referencialTeacherId = referencialTeacherId;
    }

    public Mccc() {
    }

    public McccId getMcccId() {
        return mcccId;
    }

    public void setMcccId(McccId mcccId) {
        this.mcccId = mcccId;
    }

    public HourlyVolume getHourlyVolId() {
        return hourlyVolId;
    }

    public void setHourlyVolId(HourlyVolume hourlyVolId) {
        this.hourlyVolId = hourlyVolId;
    }

    public Resource getResourceId() {
        return resourceId;
    }

    public void setResourceId(Resource resourceId) {
        this.resourceId = resourceId;
    }

    public Set<Sae> getSaeId() {
        return saeId;
    }

    public void setSaeId(Set<Sae> saeId) {
        this.saeId = saeId;
    }

    public Skill getSkillId() {
        return skillId;
    }

    public void setSkillId(Skill skillId) {
        this.skillId = skillId;
    }

    public Set<Teacher> getReferencialTeacherId() {
        return referencialTeacherId;
    }

    public void setReferencialTeacherId(Set<Teacher> referencialTeacherId) {
        this.referencialTeacherId = referencialTeacherId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }
}
