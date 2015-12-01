package com.driftdirect.dto.championship;

import com.driftdirect.dto.round.RoundShortShowDto;

/**
 * Created by Paul on 11/29/2015.
 */
public class ChampionshipShortShowDto {
    private Long id;
    private Long backgroundImage;
    private Long logo;
    private RoundShortShowDto nextRound;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Long backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    public RoundShortShowDto getNextRound() {
        return nextRound;
    }

    public void setNextRound(RoundShortShowDto nextRound) {
        this.nextRound = nextRound;
    }

}
