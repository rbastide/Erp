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

    @ManyToMany
    @JoinTable(
            name = "McccSaes",
            joinColumns = @JoinColumn(name = "mcccSaesID"),
            inverseJoinColumns = @JoinColumn(name = "saeID")
    )
    private Set<Sae> saesId;

    public Mccc(McccId mcccId, HourlyVolume hourlyVolId, Resource resourceId, Set<Sae> saesId, Set<CriticalLearning> criticalLearningsId, Set<Teacher> referencialTeacherId) {
        this.mcccId = mcccId;
        this.hourlyVolId = hourlyVolId;
        this.resourceId = resourceId;
        this.saesId = saesId;
        this.criticalLearningsId = criticalLearningsId;
        this.referencialTeacherId = referencialTeacherId;
    }

    @ManyToOne
    @JoinColumn(name = "hourlyVolID")
    private HourlyVolume hourlyVolId;

    @ManyToOne
    @JoinColumn(name = "resourceID")
    private Resource resourceId;

    public void setYear(int year) {
        this.year = year;
    }

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

    public void setSemester(int semester) {
        this.semester = semester;
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