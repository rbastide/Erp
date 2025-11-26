package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.service.PedagologicalTeachersFeedbacksService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pedagologicalTeachersFeedbacks")
public class PedagologicalTeachersFeedbacksController {

    private PedagologicalTeachersFeedbacksService pedagologicalTeachersFeedbackService;

    public PedagologicalTeachersFeedbacksController(PedagologicalTeachersFeedbacksService pedagologicalTeachersFeedbackService) {
        this.pedagologicalTeachersFeedbackService = pedagologicalTeachersFeedbackService;
    }
}
