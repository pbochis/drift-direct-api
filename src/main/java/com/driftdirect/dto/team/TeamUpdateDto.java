package com.driftdirect.dto.team;

/**
 * Created by Paul on 11/27/2015.
 */
public class TeamUpdateDto extends TeamCreateDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
