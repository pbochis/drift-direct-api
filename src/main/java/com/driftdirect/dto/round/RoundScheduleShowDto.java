package com.driftdirect.dto.round;

import org.joda.time.DateTime;

/**
 * Created by Paul on 11/18/2015.
 */
public class RoundScheduleShowDto {
    private String name;
    private DateTime startDate;
    private DateTime endDate;

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
