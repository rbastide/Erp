package fr.iut_unilim.erp_back.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "AutomaticMailConfig")
public class AutomaticMailConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "configID")
    private Long id;

    private String label;
    private int dayOfMonth;
    private String time; // Format "HH:mm"
    private boolean isOneTime;

    private String months;

    public AutomaticMailConfig() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isOneTime() {
        return isOneTime;
    }

    public void setOneTime(boolean oneTime) {
        isOneTime = oneTime;
    }

    public String getMonths() {
        return months;
    }

    public void setMonthsFromList(List<Integer> monthList) {
        if (monthList == null || monthList.isEmpty()) {
            this.months = "*";
        } else {
            this.months = monthList.stream().map(String::valueOf).collect(Collectors.joining(","));
        }
    }

    public List<Integer> getMonthsAsList() {
        if (this.months == null || this.months.equals("*") || this.months.isEmpty()) return new ArrayList<>();
        return Arrays.stream(this.months.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }
}