package fr.iut_unilim.erp_back.dto;

public class SaeRequest {
    private Long saeID;
    private String num;
    private String title;

    public Long getSaeID() {
        return saeID;
    }

    public String getTitle() {
        return title;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setSaeID(Long saeID) {
        this.saeID = saeID;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
