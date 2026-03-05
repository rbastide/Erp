package fr.iut_unilim.erp_back.dto;

import java.util.List;
import java.util.Map;

public record ResourceSheetRequest(Long resourceSheetID, Long resourceID, Map<String, Long> courseHours,
                                   String teacherFeedbacks, String studentFeedbacks, String improvementsIdeas,
                                   Map<String, List<String>> educationalContents, String mainGoal) {
}
