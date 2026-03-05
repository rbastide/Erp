package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.CourseHoursResponse;
import fr.iut_unilim.erp_back.dto.ResourceSheetResponse;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.repository.ResourceRepository;
import fr.iut_unilim.erp_back.repository.ResourceSheetRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceSheetService {

    private final ResourceSheetRepository resourceSheetRepository;
    private final ConnectionService connectionService;
    private final ResourceRepository resourceRepository;
    private final EducationalContentService educationalContentService;

    public ResourceSheetService(ResourceSheetRepository resourceSheetRepository, ConnectionService connectionService, ResourceRepository resourceRepository, EducationalContentService educationalContentService) {
        this.resourceSheetRepository = resourceSheetRepository;
        this.connectionService = connectionService;
        this.resourceRepository = resourceRepository;
        this.educationalContentService = educationalContentService;
    }

    public List<ResourceSheet> getAllResourceSheets() {
        return resourceSheetRepository.findAll();
    }

    public List<ResourceSheet> getAllResourceSheetsFromDepartment(@NotNull String identifier) {
        Connection senderConnection = connectionService.findByIdentifier(identifier);
        UniversityDepartment department = senderConnection.getUniversityDepartment();

        List<ResourceSheet> resourceSheets = new ArrayList<>();
        for (Resource resource : resourceRepository.findAllByUniversityDepartment(department)) {
            resourceSheets.addAll(resourceSheetRepository.findAllByResource(resource));
        }

        return resourceSheets;
    }

    public ResourceSheet save(ResourceSheet resourceSheet) {
        return resourceSheetRepository.save(resourceSheet);
    }

    public void deleteResourceSheetById(Long id){
        if(!resourceSheetRepository.existsById(id)){
            return;
        }
        resourceSheetRepository.deleteById(id);
    }

    public Optional<ResourceSheet> getResourceSheetFromId(@NotNull Long id) {
        return resourceSheetRepository.findById(id);
    }

    public List<ResourceSheetResponse> convertToEntitiesToResponses(List<ResourceSheet> resourceSheets) {
        return resourceSheets.stream().map(this::convertToEntityToResponse).toList();
    }

    public ResourceSheetResponse convertToEntityToResponse(ResourceSheet resourceSheet) {
        CourseHours courseHours = resourceSheet.getCourseHours();

        CourseHoursResponse courseHoursResponse = new CourseHoursResponse(
                courseHours.getNbHoursCM(),
                courseHours.getNbHoursTD(),
                courseHours.getNbHoursTP(),
                courseHours.getNbHoursDS(),
                courseHours.getNbHoursDSTP()
        );
        return new ResourceSheetResponse(resourceSheet.getSheetID(),
                resourceSheet.getResource().getResourceID(),
                courseHoursResponse,
                resourceSheet.getCreationDate(),
                resourceSheet.getLastModificationDate(),
                resourceSheet.getImprovementIdeas(),
                resourceSheet.getTeacherFeedbacks(),
                resourceSheet.getStudentFeedbacks(),
                educationalContentService.convertEntitiesToResponses(resourceSheet.getEducationalContents()),
                resourceSheet.isValidate()
        );
    }
}
