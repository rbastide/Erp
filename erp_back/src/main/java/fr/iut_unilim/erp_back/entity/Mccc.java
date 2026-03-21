package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Mccc")
public class Mccc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mcccID")
    private Long mcccId;

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

    @OneToMany
    @JoinColumn(name = "resourceID", referencedColumnName = "resourceID", insertable = false, updatable = false)
    private Set<TeacherResource> teacherResources;

    @ManyToOne
    @JoinColumn(name = "universityDepartmentID")
    private UniversityDepartment universityDepartment;

    private Date creationDate;
    private Date lastModificationDate;

    @JoinColumn(name ="academicYearStart")
    private Integer academicYearStart;

    public Mccc(CourseHours courseHoursId, Resource resourceId, Set<Sae> saesId, Set<CriticalConcept> criticalConceptsId, Set<TeacherResource> teacherResources, UniversityDepartment universityDepartment, Integer academicYearStart) {
        this.courseHoursId = courseHoursId;
        this.resourceId = resourceId;
        this.saesId = saesId;
        this.criticalConceptsId = criticalConceptsId;
        this.teacherResources = teacherResources;
        this.universityDepartment = universityDepartment;
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

    public UniversityDepartment getUniversityDepartment() { return universityDepartment; }

    public void setUniversityDepartment(UniversityDepartment universityDepartmentId) { this.universityDepartment = universityDepartmentId; }

    public Integer getAcademicYearStart() {
        return academicYearStart;
    }

    public void setAcademicYearStart(Integer academicYearStart) {
        this.academicYearStart = academicYearStart;
    }
}