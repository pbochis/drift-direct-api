package com.driftdirect.dto.round;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Paul on 11/14/2015.
 */
public class RoundCreateDto {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    private Long championshipId;

    private Long logo;

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getChampionshipId() {
        return championshipId;
    }

    public void setChampionshipId(Long championshipId) {
        this.championshipId = championshipId;
    }
}
