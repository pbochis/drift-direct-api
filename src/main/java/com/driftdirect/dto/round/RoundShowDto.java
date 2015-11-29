package com.driftdirect.dto.round;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Paul on 11/14/2015
 */
public class RoundShowDto {
    private long id;
    private String name;
    private DateTime startDate;
    private DateTime endDate;

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

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }
}