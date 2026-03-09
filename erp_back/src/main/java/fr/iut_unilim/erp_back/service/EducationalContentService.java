package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.EducationalContentResponse;
import fr.iut_unilim.erp_back.entity.EducationalContent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationalContentService {
    public EducationalContentResponse convertEntityToResponse(EducationalContent educationalContent) {
        return new EducationalContentResponse(
                educationalContent.getClassType().getClassTypeName(),
                educationalContent.getCourseNumber(),
                educationalContent.getContent()
        );
    }

    public List<EducationalContentResponse> convertEntitiesToResponses(List<EducationalContent> educationalContents) {
        return educationalContents.stream().map(this::convertEntityToResponse).toList();
    }
}
