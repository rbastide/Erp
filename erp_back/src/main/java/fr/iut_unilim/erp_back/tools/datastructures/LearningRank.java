package fr.iut_unilim.erp_back.tools.datastructures;

import java.util.List;

public record LearningRank(String resourceCode, String ue, String rank, List<String> acs) {
}
