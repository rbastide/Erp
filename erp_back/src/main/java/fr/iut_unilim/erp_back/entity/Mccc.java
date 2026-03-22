package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Mccc")
public class Mccc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mcccID")
    private Long mcccId;

    @JoinColumn(name = "academicYearStart")
    private Integer academicYearStart;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "courseHoursID")
    private CourseHours courseHoursId;

    @ManyToOne
    @JoinColumn(name = "resourceID")
    private Resource resourceId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "L_McccSae",
            joinColumns = @JoinColumn(name = "mcccID"),
            inverseJoinColumns = @JoinColumn(name = "saeID")
    )
    private Set<Sae> saesId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "L_McccCriticalConcept",
            joinColumns = @JoinColumn(name = "mcccID"),
            inverseJoinColumns = @JoinColumn(name = "criticalConceptID")
    )
    private Set<CriticalConcept> criticalConceptsId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "resourceID", referencedColumnName = "resourceID", insertable = false, updatable = false)
    private Set<TeacherResource> teacherResources;

    private LocalDateTime creationDate;
    private LocalDateTime lastModificationDate;

    public Mccc(CourseHours courseHoursId, Resource resourceId, Set<Sae> saesId, Set<CriticalConcept> criticalConceptsId, Set<TeacherResource> teacherResources, Integer academicYearStart) {
        this.courseHoursId = courseHoursId;
        this.resourceId = resourceId;
        this.saesId = saesId;
        this.criticalConceptsId = criticalConceptsId;
        this.teacherResources = teacherResources;
        this.academicYearStart = academicYearStart;
    }

    public Mccc() {
    }

    public Long getMcccId() {
        return mcccId;
    }

    public void setMcccId(Long mcccId) {
        this.mcccId = mcccId;
    }

    public CourseHours getCourseHoursId() {
        return courseHoursId;
    }

    public void setCourseHoursId(CourseHours courseHours) {
        this.courseHoursId = courseHours;
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

    public Set<CriticalConcept> getCriticalConceptsId() {
        return criticalConceptsId;
    }

    public void setCriticalConceptsId(Set<CriticalConcept> skillId) {
        this.criticalConceptsId = skillId;
    }

    public Set<TeacherResource> getTeacherResources() {
        return teacherResources;
    }

    public void setTeacherResources(Set<TeacherResource> teacherResources) {
        this.teacherResources = teacherResources;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDateTime lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Integer getAcademicYearStart() {
        return academicYearStart;
    }

    public void setAcademicYearStart(Integer academicYearStart) {
        this.academicYearStart = academicYearStart;
    }
}