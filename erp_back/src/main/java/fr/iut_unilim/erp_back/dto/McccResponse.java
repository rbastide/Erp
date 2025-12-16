package fr.iut_unilim.erp_back.dto;

import fr.iut_unilim.erp_back.tools.datastructures.Skill;
import fr.iut_unilim.erp_back.tools.datastructures.Teacher;

public class McccResponse {
    private int resourceCode;
    private String resourceName;
    private String mainGoal;
    private int year;
    private int semester;

    private int hoursCM;
    private int hoursTD;
    private int hoursTP;
    private int hoursDS;
    private int hoursDSTP;
    private int hoursTotal;


    private String[] saeCodes;
    private Skill[] competence;

    private Teacher[] referents;
}
