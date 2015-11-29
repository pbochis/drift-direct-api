package com.driftdirect.dto.round;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Paul on 11/14/2015
 */
public class RoundShowDto {
    private long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private List<RoundScheduleShowDto> schedule;

    public List<RoundScheduleShowDto> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<RoundScheduleShowDto> schedule) {
        this.schedule = schedule;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
