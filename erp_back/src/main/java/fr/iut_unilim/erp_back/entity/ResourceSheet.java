package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ResourceSheet")
public class ResourceSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "sheetsID")
    private Long sheetsID;

    @Column(name = "resourceID")
    private Long resourceID;

    @Column(name = "referencialTeacherID")
    private Long referencialTeacherID;

    @Column(name = "hourlyVolumeID")
    private Long hourlyVolumeID;

    @Column(name = "teachersReturnID")
    private Long teachersReturnID;

    @Column(name = "studentReturnID")
    private Long studentReturnID;

    @Column(name = "improvementsIdeaID")
    private Long improvementsIDeaID;

    @Column(name = "semester")
    private int semester;

    @Column(name = "year")
    private int year;

    @Column(name = "mainGoal")
    private String mainGoal;

    @Column(name = "content")
    private String content;

    @Column(name = "linkedSAE")
    private int linkedSAE;

    @Column(name = "creationDate")
    private String creationDate;

    @ManyToMany(mappedBy = "resourceSheets")
    private List<Teacher> teachers;

    @ManyToMany(mappedBy = "resourceSheets")
    private List<Skill> skills;

    public ResourceSheet() {
    }

    public ResourceSheet(Long sheetsID, Long resourceID, Long referencialTeacherID, Long hourlyVolumeID, Long teachersReturnID, Long studentReturnID, Long improvementsIDeaID, int semester, int year, String mainGoal, String content, int linkedSAE) {
        this.sheetsID = sheetsID;
        this.resourceID = resourceID;
        this.referencialTeacherID = referencialTeacherID;
        this.hourlyVolumeID = hourlyVolumeID;
        this.teachersReturnID = teachersReturnID;
        this.studentReturnID = studentReturnID;
        this.improvementsIDeaID = improvementsIDeaID;
        this.semester = semester;
        this.year = year;
        this.mainGoal = mainGoal;
        this.content = content;
        this.linkedSAE = linkedSAE;
    }

    public Long sheetsID() { return sheetsID; }
    public Long resourceID() { return resourceID; }
    public Long referencialTeacherID() { return referencialTeacherID; }
    public Long hourlyVolumeID() { return hourlyVolumeID; }
    public Long teachersReturnID() { return teachersReturnID; }
    public Long studentReturnID() { return studentReturnID; }
    public Long improvementsID() { return improvementsIDeaID; }
    public int semester() { return semester; }
    public int year() { return year; }
    public String mainGoal() { return mainGoal; }
    public String content() { return content; }
    public int linkedSAE() { return linkedSAE; }
}
