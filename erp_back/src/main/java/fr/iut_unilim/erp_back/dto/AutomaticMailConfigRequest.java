package fr.iut_unilim.erp_back.dto;

import java.util.List;

public record AutomaticMailConfigRequest(
        Long id,
        String label,
        int day,
        String time,
        boolean isOneTime,
        List<Integer> months
) {
}