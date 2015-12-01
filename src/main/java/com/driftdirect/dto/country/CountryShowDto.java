package com.driftdirect.dto.country;

/**
 * Created by Paul on 11/30/2015.
 */
public class CountryShowDto {
    private Long id;
    private String name;
    private Long flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }
}
