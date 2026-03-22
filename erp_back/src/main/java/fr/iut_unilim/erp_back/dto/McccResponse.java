package fr.iut_unilim.erp_back.dto;


import fr.iut_unilim.erp_back.entity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public record McccResponse(
        Long mcccId,
        CourseHoursResponse courseHoursId,
        ResourceResponse resourceId,
        List<SaeResponse> saesId,
        List<CriticalConceptResponse> criticalConceptsId,
        List<TeacherResponse> referencialTeacherId,
        LocalDateTime creationDate,
        LocalDateTime lastModificationDate
) {
    public McccResponse(Mccc mccc) {
        this(
                mccc.getMcccId(),
                mccc.getCourseHoursId() == null ? null : new CourseHoursResponse(mccc.getCourseHoursId()),
                mccc.getResourceId() == null ? null : new ResourceResponse(mccc.getResourceId()),
                mapSaes(mccc.getSaesId()),
                mapCriticalConcepts(mccc.getCriticalConceptsId()),
                mapTeachers(mccc.getTeacherResources()),
                mccc.getCreationDate(),
                mccc.getLastModificationDate()
        );
    }


    private static List<SaeResponse> mapSaes(Set<Sae> saes) {
        if (saes == null) return List.of();
        return saes.stream().map(SaeResponse::new).toList();
    }


    private static List<CriticalConceptResponse> mapCriticalConcepts(Set<CriticalConcept> concepts) {
        if (concepts == null) return List.of();
        return concepts.stream().map(CriticalConceptResponse::new).toList();
    }


    private static List<TeacherResponse> mapTeachers(Set<TeacherResource> teachers) {
        if (teachers == null) return List.of();
        return teachers.stream().map(teacherResource -> new TeacherResponse(
                teacherResource.getConnection().getLastName(),
                teacherResource.getConnection().getFirstName()
        )).toList();
    }


    public record CourseHoursResponse(
            Long courseHoursID,
            Float nbHoursCM,
            Float nbHoursDSTP,
            Float nbHoursDS,
            Float nbHoursTP,
            Float nbHoursTD
    ) {
        public CourseHoursResponse(CourseHours courseHours) {
            this(
                    courseHours.getCourseHoursID(),
                    courseHours.getNbMinCM(),
                    courseHours.getNbMinDSTP(),
                    courseHours.getNbMinDS(),
                    courseHours.getNbMinTP(),
                    courseHours.getNbMinTD()
            );
        }
    }


    public record ResourceResponse(
            Long resourceID,
            String num,
            String name,
            int semester
    ) {
        public ResourceResponse(Resource resource) {
            this(
                    resource.getResourceID(),
                    resource.getNum(),
                    resource.getName(),
                    resource.getSemester()
            );
        }
    }


    public record SaeResponse(
            Long saeID,
            String num,
            String title
    ) {
        public SaeResponse(Sae sae) {
            this(sae.getSaeID(), sae.getNum(), sae.getTitle());
        }
    }

    public record CriticalConceptResponse(
            Long criticalConceptID,
            int criticalConceptNum,
            String criticalConceptTitle,
            int learningNum,
            String learningTitle,
            RankResponse rankID
    ) {
        public CriticalConceptResponse(CriticalConcept criticalConcept) {
            this(
                    criticalConcept.getCriticalConceptID(),
                    criticalConcept.getCriticalConceptNum(),
                    criticalConcept.getCriticalConceptTitle(),
                    criticalConcept.getCriticalConceptNum(),
                    criticalConcept.getCriticalConceptTitle(),
                    criticalConcept.getRankID() == null ? null : new RankResponse(criticalConcept.getRankID())
            );
        }
    }


    public record RankResponse(
            Long rankID,
            int rankNum,
            String rankTitle,
            SkillResponse skillID
    ) {
        public RankResponse(Rank rank) {
            this(
                    rank.getRankID(),
                    rank.getRankNum(),
                    rank.getRankTitle(),
                    rank.getSkillID() == null ? null : new SkillResponse(rank.getSkillID())
            );
        }
    }


    public record SkillResponse(
            Long skillID,
            int skillNum,
            String skillName
    ) {
        public SkillResponse(Skill skill) {
            this(skill.getSkillID(), skill.getSkillNum(), skill.getSkillName());
        }
    }
}
