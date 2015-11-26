package com.driftdirect.dto.person;

/**
 * Created by Paul on 11/25/2015.
 */
public class DriverDetailsUpdateDto extends DriverDetailsCreateDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
