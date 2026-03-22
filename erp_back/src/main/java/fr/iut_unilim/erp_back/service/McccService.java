package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.McccRequest;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.model.TeacherMccModel;
import fr.iut_unilim.erp_back.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class McccService {
    private final McccRepository mcccRepository;
    private final ResourceRepository resourceRepository;
    private final CourseHoursService courseHoursService;
    private final SkillRepository skillRepository;
    private final SaeRepository saeRepository;
    private final TeacherResourceRepository teacherResourceRepository;
    private final ConnectionRepository connectionRepository;

    public McccService(McccRepository mcccRepository, ResourceRepository resourceRepository, CourseHoursService courseHoursService, SkillRepository skillRepository, SaeRepository saeRepository, TeacherResourceRepository teacherResourceRepository, ConnectionRepository connectionRepository) {
        this.mcccRepository = mcccRepository;
        this.resourceRepository = resourceRepository;
        this.courseHoursService = courseHoursService;
        this.skillRepository = skillRepository;
        this.saeRepository = saeRepository;
        this.teacherResourceRepository = teacherResourceRepository;
        this.connectionRepository = connectionRepository;
    }

    public Optional<Mccc> findById(Long id) {
        return mcccRepository.findByMcccId(id);
    }

    public Optional<Mccc> saveFromDto(McccRequest mcccRequest) {
        Optional<Resource> resourceOptional = resourceRepository.findById(mcccRequest.resourceID());
        if (resourceOptional.isEmpty()) return Optional.empty();

        Optional<Mccc> mcccOptional = mcccRepository.findByResourceIdAndAcademicYearStart(resourceOptional.get(), mcccRequest.year());
        Mccc mccc = mcccOptional.orElse(createNewMccc());
        mccc.setLastModificationDate(LocalDateTime.now());
        mccc.setResourceId(resourceOptional.get());
        mccc.setAcademicYearStart(mcccRequest.year());
        mccc.setCourseHoursId(getCorrespondingCourseHours(mcccRequest));

        try {
            mccc.setCriticalConceptsId(getCriticalConceptsFromDto(mcccRequest));
            mccc.setSaesId(getSaesFromDto(mcccRequest));
            mccc.setTeacherResources(getTeacherResourcesFromDto(mcccRequest, resourceOptional.get()));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }

        mcccRepository.save(mccc);

        return Optional.of(mccc);
    }

    @NotNull
    private Mccc createNewMccc() {
        Mccc mccc = new Mccc();
        mccc.setCreationDate(LocalDateTime.now());
        return mccc;
    }

    @NotNull
    private CourseHours getCorrespondingCourseHours(McccRequest mcccRequest) {
        return courseHoursService.findOrCreateCourseHoursFromHours(
                mcccRequest.minCM(),
                mcccRequest.minTD(),
                mcccRequest.minTP(),
                mcccRequest.minDSTP(),
                mcccRequest.minDS()
        );
    }

    @NotNull
    private Set<CriticalConcept> getCriticalConceptsFromDto(McccRequest mcccRequest) {
        Set<CriticalConcept> criticalConcepts = new HashSet<>();
        Long[] skillIds = mcccRequest.skillIDs();
        for (Long skillId : skillIds) {
            Optional<Skill> skillOptional = skillRepository.findById(skillId);
            Skill skill = skillOptional.orElseThrow();
            List<Rank> ranks = skill.getRanks();
            for (Rank rank : ranks) {
                List<CriticalConcept> criticalConceptsFromRank = rank.getCriticalConcepts();
                criticalConcepts.addAll(criticalConceptsFromRank);
            }
        }

        return criticalConcepts;
    }

    @NotNull
    private Set<Sae> getSaesFromDto(McccRequest mcccRequest) {
        Set<Sae> saes = new HashSet<>();
        Long[] saeIds = mcccRequest.saeIDs();
        for (Long saeId : saeIds) {
            Optional<Sae> saeOptional = saeRepository.findById(saeId);
            Sae sae = saeOptional.orElseThrow();
            saes.add(sae);
        }

        return saes;
    }

    @NotNull
    private Set<TeacherResource> getTeacherResourcesFromDto(McccRequest mcccRequest, Resource resource) {
        Set<TeacherResource> teacherResources = new HashSet<>();
        TeacherMccModel[] teacherMccModels = mcccRequest.teachers();
        for (TeacherMccModel teacherMccModel : teacherMccModels) {
            Optional<Connection> connectionOptional = connectionRepository.findById(teacherMccModel.teacherID());
            Connection connection = connectionOptional.orElseThrow();
            Optional<TeacherResource> teacherResourceOptional = teacherResourceRepository.findByConnectionAndResource(
                    connection,
                    resource
            );
            TeacherResource teacherResource = teacherResourceOptional.orElseGet(() ->
                    new TeacherResource(connectionOptional.orElseThrow(), resource)
            );
            teacherResource.setIsManager(teacherMccModel.isManager());
            teacherResources.add(teacherResource);
        }

        return teacherResources;
    }

    public Set<Integer> getAllYears() {
        List<Mccc> mcccs = mcccRepository.findAll();
        Set<Integer> years = new HashSet<>();
        for (Mccc mccc : mcccs) {
            years.add(mccc.getAcademicYearStart());
        }
        return years;
    }

    public Optional<Mccc> getCurrentMcccFromResource(Resource resource, Integer academicYearStart) {
        return mcccRepository.findByResourceIdAndAcademicYearStart(resource, academicYearStart);
    }

    public Set<Skill> getSkillsByResource(Resource resource, Integer academicYearStart) {
        Optional<Mccc> mcccOptional = getCurrentMcccFromResource(resource, academicYearStart);
        if (mcccOptional.isEmpty()) return null;

        Mccc mccc = mcccOptional.get();
        Set<CriticalConcept> criticalConcepts = mccc.getCriticalConceptsId();
        Set<Skill> skills = new HashSet<>();
        for (CriticalConcept criticalConcept : criticalConcepts) {
            Rank rank = criticalConcept.getRankID();
            Skill skill = rank.getSkillID();
            skills.add(skill);
        }

        return skills;
    }
}