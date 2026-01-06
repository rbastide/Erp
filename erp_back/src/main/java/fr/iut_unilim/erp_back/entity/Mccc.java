package fr.iut_unilim.erp_back.entity;

import fr.iut_unilim.erp_back.tools.datastructures.McccId;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "MCCC")
public class Mccc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private McccId mcccId;

    private int year;
    private int semester;
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

    @ManyToMany
    @JoinTable(
            name = "McccCriticalLearning",
            joinColumns = @JoinColumn(name = "mcccLearningID"),
            inverseJoinColumns = @JoinColumn(name = "learningID")
    )
    private Set<CriticalLearning> criticalLearningsId;

    @ManyToMany
    @JoinTable(
            name = "MCCCTeachers",
            joinColumns = @JoinColumn(name = "referencialTeacher"),
            inverseJoinColumns = @JoinColumn(name = "teacherID")
    )
    private Set<Teacher> referencialTeacherId;

    private Date creationDate;
    private Date lastModificationDate;

    public Mccc(McccId mcccId, HourlyVolume hourlyVolId, Resource resourceId, Set<Sae> saeId, Set<CriticalLearning> criticalLearningsId, Set<Teacher> referencialTeacherId) {
        this.mcccId = mcccId;
        this.hourlyVolId = hourlyVolId;
        this.resourceId = resourceId;
        this.saeId = saeId;
        this.criticalLearningsId = criticalLearningsId;
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

    public Set<CriticalLearning> getCriticalLearningsId() {
        return criticalLearningsId;
    }

    public void setCriticalLearningsId(Set<CriticalLearning> skillId) {
        this.criticalLearningsId = skillId;
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