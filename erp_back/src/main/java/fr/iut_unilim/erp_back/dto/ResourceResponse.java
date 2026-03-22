package fr.iut_unilim.erp_back.dto;


import fr.iut_unilim.erp_back.entity.Resource;

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
