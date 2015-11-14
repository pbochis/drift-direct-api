package com.driftdirect.dto.championship;

/**
 * Created by Paul on 11/11/2015.
 */
public class ChampionshipUpdateDTO extends ChampionshipCreateDTO {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
