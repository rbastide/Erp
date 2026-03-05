package fr.iut_unilim.erp_back.dto;

import java.util.Date;
import java.util.List;

public record ResourceSheetResponse(long resourceSheetId, long resourceId, CourseHoursResponse courseHours,
                                    Date creationDate, Date lastModificationDate, String improvementIdeas,
                                    String teacherFeedbacks, String studentFeedbacks,
                                    List<EducationalContentResponse> educationalContentID, boolean isValidate) {
}
