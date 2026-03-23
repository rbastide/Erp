package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.CourseHoursResponse;
import fr.iut_unilim.erp_back.dto.HistoryResponse;
import fr.iut_unilim.erp_back.dto.ResourceSheetRequest;
import fr.iut_unilim.erp_back.dto.ResourceSheetResponse;
import fr.iut_unilim.erp_back.entity.*;
import fr.iut_unilim.erp_back.repository.*;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResourceSheetService {

    private final ResourceSheetRepository resourceSheetRepository;
    private final ConnectionService connectionService;
    private final ResourceRepository resourceRepository;
    private final EducationalContentService educationalContentService;
    private final ClassTypeRepository classTypeRepository;
    private final CourseHoursService courseHoursService;
    private final ImprovementIdeaRepository improvementIdeaRepository;
    private final StudentFeedbackRepository studentFeedbackRepository;
    private final TeacherFeedbackRepository teacherFeedbackRepository;
    private final SanitizeService sanitizeService;

    public ResourceSheetService(ResourceSheetRepository resourceSheetRepository, ConnectionService connectionService, ResourceRepository resourceRepository, EducationalContentService educationalContentService, ClassTypeRepository classTypeRepository, CourseHoursService courseHoursService, ImprovementIdeaRepository improvementIdeaRepository, StudentFeedbackRepository studentFeedbackRepository, TeacherFeedbackRepository teacherFeedbackRepository, SanitizeService sanitizeService, SanitizeService sanitizeService1) {
        this.resourceSheetRepository = resourceSheetRepository;
        this.connectionService = connectionService;
        this.resourceRepository = resourceRepository;
        this.educationalContentService = educationalContentService;
        this.classTypeRepository = classTypeRepository;
        this.courseHoursService = courseHoursService;
        this.improvementIdeaRepository = improvementIdeaRepository;
        this.studentFeedbackRepository = studentFeedbackRepository;
        this.teacherFeedbackRepository = teacherFeedbackRepository;
        this.sanitizeService = sanitizeService1;
    }

    public List<ResourceSheet> getAllResourceSheetsFromDepartmentAndYear(@NotNull String identifier, Integer year) {
        Connection senderConnection = connectionService.findByIdentifier(identifier);
        UniversityDepartment department = senderConnection.getUniversityDepartment();

        List<ResourceSheet> resourceSheets = new ArrayList<>();
        for (Resource resource : resourceRepository.findAllByUniversityDepartment(department)) {
            resourceSheets.addAll(resourceSheetRepository.findAllByResourceAndAcademicYearStart(resource, year));
        }

        return resourceSheets;
    }

    public Optional<ResourceSheet> getResourceSheetForResourceFromYear(Long resourceID, int academicYearStart) {
        Optional<Resource> resource = resourceRepository.findById(resourceID);
        if (resource.isEmpty()) return Optional.empty();

        return resourceSheetRepository.findByResourceAndAcademicYearStart(resource.get(), academicYearStart);
    }

    public ResourceSheet save(ResourceSheet resourceSheet) {
        return resourceSheetRepository.save(resourceSheet);
    }

    public boolean deleteResourceSheetById(Long id) {
        if (!resourceSheetRepository.existsById(id)){
            return false;
        }
        resourceSheetRepository.deleteById(id);
        return true;
    }

    public Optional<ResourceSheet> getResourceSheetById(Long id) {
        return resourceSheetRepository.findById(id);
    }

    public List<ResourceSheetResponse> convertToEntitiesToResponses(List<ResourceSheet> resourceSheets) {
        return resourceSheets.stream().map(this::convertEntityToResponse).toList();
    }

    public Set<Integer> getAllYears() {
        List<ResourceSheet> resourceSheets = resourceSheetRepository.findAll();
        Set<Integer> years = new HashSet<>();
        for (ResourceSheet resourceSheet : resourceSheets) {
            years.add(resourceSheet.getAcademicYearStart());
        }
        return years;
    }

    public ResourceSheetResponse convertEntityToResponse(ResourceSheet resourceSheet) {
        CourseHours courseHours = resourceSheet.getCourseHours();

        CourseHoursResponse courseHoursResponse = new CourseHoursResponse(
                courseHours.getNbMinCM(),
                courseHours.getNbMinTD(),
                courseHours.getNbMinTP(),
                courseHours.getNbMinDS(),
                courseHours.getNbMinDSTP()
        );
        return new ResourceSheetResponse(resourceSheet.getSheetID(),
                resourceSheet.getResource().getResourceID(),
                courseHoursResponse,
                resourceSheet.getCreationDate(),
                resourceSheet.getLastModificationDate(),
                resourceSheet.getImprovementIdeas().getContent(),
                resourceSheet.getTeacherFeedbacks().getContent(),
                resourceSheet.getStudentFeedbacks().getContent(),
                educationalContentService.convertEntitiesToResponses(resourceSheet.getEducationalContents()),
                resourceSheet.isValidate(),
                resourceSheet.getAcademicYearStart()
        );
    }

    public boolean saveFromRequest(ResourceSheetRequest resourceSheetRequest) {
        ResourceSheet resourceSheet = createOrExtractResourceSheetFromRequest(resourceSheetRequest);
        if (resourceSheet == null) return false;

        resourceSheet.setLastModificationDate(new Date());
        fillFeedbacks(resourceSheetRequest, resourceSheet);
        resourceSheet.setValidate(false);
        resourceSheet.setAcademicYearStart(resourceSheetRequest.academicYearStart());

        List<EducationalContent> newContents = extractEducationalContentsFromRequest(resourceSheetRequest, resourceSheet);
        if (newContents == null) return false;

        if (resourceSheet.getEducationalContents() == null) {
            resourceSheet.setEducationalContents(new ArrayList<>(newContents));
        } else {
            resourceSheet.getEducationalContents().clear();
            resourceSheet.getEducationalContents().addAll(newContents);
        }

        Optional<Resource> resource = resourceRepository.findById(resourceSheetRequest.resourceID());
        if (resource.isEmpty()) return false;

        resourceSheet.setResource(resource.get());
        resourceSheet.setCourseHours(courseHoursService.findOrCreateCourseHoursFromHours(
                resourceSheetRequest.courseHours().get("cm"),
                resourceSheetRequest.courseHours().get("td"),
                resourceSheetRequest.courseHours().get("tp"),
                resourceSheetRequest.courseHours().get("ds_tp"),
                resourceSheetRequest.courseHours().get("ds")
        ));

        resourceSheetRepository.save(resourceSheet);
        return true;
    }

    public List<HistoryResponse> getHistoryResponses(String identifier, Integer year) {
        List<ResourceSheet> sheets = getAllResourceSheetsFromDepartmentAndYear(identifier, year);
        List<HistoryResponse> historyList = new ArrayList<>();

        for (ResourceSheet sheet : sheets) {
            Resource resource = sheet.getResource();
            String code = resource.getNum();
            String name = resource.getName();

            Date dateToUse = sheet.getLastModificationDate() != null ?
                    sheet.getLastModificationDate() : sheet.getCreationDate();

            historyList.add(new HistoryResponse(
                    sheet.getSheetID(),
                    code,
                    name,
                    dateToUse,
                    sheet.isValidate(),
                    sheet.getAcademicYearStart()));
        }

        Collections.reverse(historyList);

        return historyList;
    }

    @Nullable
    private ResourceSheet createOrExtractResourceSheetFromRequest(ResourceSheetRequest resourceSheetRequest) {
        ResourceSheet resourceSheet;
        if (resourceSheetRequest.resourceSheetID() == null) {
            resourceSheet = new ResourceSheet();
            resourceSheet.setCreationDate(new Date());
        } else {
            Optional<ResourceSheet> existingResourceSheet = resourceSheetRepository.findById(resourceSheetRequest.resourceSheetID());
            if (existingResourceSheet.isEmpty()) {
                return null;
            }
            resourceSheet = existingResourceSheet.get();
        }
        return resourceSheet;
    }

    @Nullable
    private List<EducationalContent> extractEducationalContentsFromRequest(ResourceSheetRequest resourceSheetRequest, ResourceSheet resourceSheet) {
        List<EducationalContent> educationalContents = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : resourceSheetRequest.educationalContents().entrySet()) {
            long currentCourseNumber = 1;
            Optional<ClassType> classType = classTypeRepository.findByClassTypeName(entry.getKey());
            if (classType.isEmpty()) return null;
            for (String content : entry.getValue()) {
                EducationalContent educationalContent = new EducationalContent();
                educationalContent.setResourceSheet(resourceSheet);
                educationalContent.setContent(content);
                educationalContent.setCourseNumber(currentCourseNumber);
                educationalContent.setClassType(classType.get());

                educationalContents.add(educationalContent);
                currentCourseNumber++;
            }
        }
        return educationalContents;
    }

    private void fillFeedbacks(ResourceSheetRequest resourceSheetRequest, ResourceSheet resourceSheet) {
        String sanitizedImprovementIdea = sanitizeService.sanitize(resourceSheetRequest.improvementsIdeas());
        String sanitizedStudentFeedback = sanitizeService.sanitize(resourceSheetRequest.studentFeedbacks());
        String sanitizedTeacherFeedback = sanitizeService.sanitize(resourceSheetRequest.teacherFeedbacks());

        Optional<ImprovementIdea> improvementIdeaOptional = improvementIdeaRepository.findByContent(sanitizedImprovementIdea);
        Optional<StudentFeedback> studentFeedbackOptional = studentFeedbackRepository.findByContent(sanitizedStudentFeedback);
        Optional<TeacherFeedback> teacherFeedbackOptional = teacherFeedbackRepository.findByContent(sanitizedTeacherFeedback);

        resourceSheet.setImprovementIdeas(improvementIdeaOptional.orElseGet(() -> new ImprovementIdea(sanitizedImprovementIdea)));
        resourceSheet.setStudentFeedbacks(studentFeedbackOptional.orElseGet(() -> new StudentFeedback(sanitizedStudentFeedback)));
        resourceSheet.setTeacherFeedbacks(teacherFeedbackOptional.orElseGet(() -> new TeacherFeedback(sanitizedTeacherFeedback)));
    }

    public Set<Long> getResourceSheetNonValidate() {
        List<ResourceSheet> resourceSheets = resourceSheetRepository.findAll();
        Set<Long> NonValidate = new HashSet<>();
        for (ResourceSheet resourceSheet : resourceSheets) {
            if(!resourceSheet.isValidate()) {
                NonValidate.add(resourceSheet.getSheetID());
            }
        }
        return NonValidate;

    }
    @Transactional
    public boolean validateSheet(Long id) {
        Optional<ResourceSheet> sheetOptional = resourceSheetRepository.findById(id);

        if(sheetOptional.isPresent()) {
            ResourceSheet resourceSheet = sheetOptional.get();

            resourceSheet.setValidate(true);

            resourceSheetRepository.save(resourceSheet);
            return true;
        }
        return false;
    }
}
