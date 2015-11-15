package com.driftdirect.dto.round;

/**
 * Created by Paul on 11/14/2015.
 */
public class RoundUpdateDto extends RoundCreateDto{
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
