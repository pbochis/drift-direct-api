package com.driftdirect.dto.round;

import java.util.Date;

/**
 * Created by Paul on 11/14/2015.
 */
public class RoundDto {
    private long id;
    private String name;
    private Date startDate;
    private Date endDate;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
