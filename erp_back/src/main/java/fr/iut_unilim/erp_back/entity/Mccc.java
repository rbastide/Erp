package fr.iut_unilim.erp_back.entity;

import fr.iut_unilim.erp_back.tools.datastructures.McccId;
import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "saeID")
    private Sae saeId;

    @ManyToOne
    @JoinColumn(name = "skillID")
    private Skill skillId;

    @ManyToOne
    @JoinColumn(name = "referencialTeacher")
    private Teacher referencialTeacherId;

    public McccId getMcccId() {
        return mcccId;
    }

    public Mccc(McccId mcccId, HourlyVolume hourlyVolId, Resource resourceId, Sae saeId, Skill skillId, Teacher referencialTeacherId) {
        this.mcccId = mcccId;
        this.hourlyVolId = hourlyVolId;
        this.resourceId = resourceId;
        this.saeId = saeId;
        this.skillId = skillId;
        this.referencialTeacherId = referencialTeacherId;
    }

    public Mccc() {
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

    public Sae getSaeId() {
        return saeId;
    }

    public void setSaeId(Sae saeId) {
        this.saeId = saeId;
    }

    public Skill getSkillId() {
        return skillId;
    }

    public void setSkillId(Skill skillId) {
        this.skillId = skillId;
    }

    public Teacher getReferencialTeacherId() {
        return referencialTeacherId;
    }

    public void setReferencialTeacherId(Teacher referencialTeacherId) {
        this.referencialTeacherId = referencialTeacherId;
    }
}
