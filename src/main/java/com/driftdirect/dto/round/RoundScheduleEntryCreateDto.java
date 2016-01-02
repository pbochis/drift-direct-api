package com.driftdirect.dto.round;

import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Paul on 11/17/2015.
 */
public class RoundScheduleEntryCreateDto {
    @NotNull
    @Size(min = 2, max = 40)
    private String name;

    @NotNull
    private DateTime startDate;
    @NotNull
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
