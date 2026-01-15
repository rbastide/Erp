package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "MCCC")
public class Mccc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mcccID")
    private Long mcccId;

    @ManyToOne
    @JoinColumn(name = "hourlyVolID")
    private HourlyVolume hourlyVolId;

    @ManyToOne
    @JoinColumn(name = "resourceID")
    private Resource resourceId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "McccSaes",
            joinColumns = @JoinColumn(name = "mcccID"),
            inverseJoinColumns = @JoinColumn(name = "saeID")
    )
    private Set<Sae> saesId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "McccCriticalLearning",
            joinColumns = @JoinColumn(name = "mcccID"),
            inverseJoinColumns = @JoinColumn(name = "learningID")
    )
    private Set<CriticalLearning> criticalLearningsId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "MCCCTeachers",
            joinColumns = @JoinColumn(name = "mcccID"),
            inverseJoinColumns = @JoinColumn(name = "teacherID")
    )
    private Set<Teacher> referencialTeacherId;


    private Date creationDate;
    private Date lastModificationDate;

    public Mccc(HourlyVolume hourlyVolId, Resource resourceId, Set<Sae> saesId, Set<CriticalLearning> criticalLearningsId, Set<Teacher> referencialTeacherId) {
        this.hourlyVolId = hourlyVolId;
        this.resourceId = resourceId;
        this.saesId = saesId;
        this.criticalLearningsId = criticalLearningsId;
        this.referencialTeacherId = referencialTeacherId;
    }

    public Mccc() {
    }

    public Long getMcccId() {
        return mcccId;
    }

    public void setMcccId(Long mcccId) {
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

    public Set<Sae> getSaesId() {
        return saesId;
    }

    public void setSaesId(Set<Sae> saeId) {
        this.saesId = saeId;
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