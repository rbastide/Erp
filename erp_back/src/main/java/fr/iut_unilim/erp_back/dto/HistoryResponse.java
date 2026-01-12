package fr.iut_unilim.erp_back.dto;

import java.util.Date;

public class HistoryResponse {
    private Long sheetID;
    private String resourceCode;
    private String resourceName;
    private Date date;

    public HistoryResponse(Long sheetID, String resourceCode, String resourceName, Date date) {
        this.sheetID = sheetID;
        this.resourceCode = resourceCode;
        this.resourceName = resourceName;
        this.date = date;
    }

    public Long getSheetID() { return sheetID; }
    public void setSheetID(Long sheetID) { this.sheetID = sheetID; }

    public String getResourceCode() { return resourceCode; }
    public void setResourceCode(String resourceCode) { this.resourceCode = resourceCode; }

    public String getResourceName() { return resourceName; }
    public void setResourceName(String resourceName) { this.resourceName = resourceName; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}