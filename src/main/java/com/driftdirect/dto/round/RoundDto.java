package com.driftdirect.dto.round;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by Paul on 11/14/2015.
 */
public class RoundDto {
    private long id;
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
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
