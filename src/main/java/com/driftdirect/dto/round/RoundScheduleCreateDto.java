package com.driftdirect.dto.round;

import java.time.ZonedDateTime;

/**
 * Created by Paul on 11/17/2015.
 */
public class RoundScheduleCreateDto {

    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }
}
