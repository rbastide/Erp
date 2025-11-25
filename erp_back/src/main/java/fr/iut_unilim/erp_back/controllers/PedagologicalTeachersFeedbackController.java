package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.entity.PedagologicalTeachersFeedback;
import fr.iut_unilim.erp_back.service.PedagologicalTeachersFeedbackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/pedagologicalTeachersFeedback")
public class PedagologicalTeachersFeedbackController {

    private PedagologicalTeachersFeedbackService pedagologicalTeachersFeedbackService;

    public PedagologicalTeachersFeedbackController(PedagologicalTeachersFeedbackService pedagologicalTeachersFeedbackService) {
        this.pedagologicalTeachersFeedbackService = pedagologicalTeachersFeedbackService;
    }
}
