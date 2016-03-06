package com.driftdirect.dto.round;

import com.driftdirect.dto.person.PersonShortShowDto;

/**
 * Created by Paul on 3/5/2016.
 */
public class RoundDriverPartialResultDto implements Comparable<RoundDriverPartialResultDto> {
    private PersonShortShowDto driver;
    private Float score;

    public RoundDriverPartialResultDto(PersonShortShowDto person, Float score) {
        this.driver = person;
        this.score = score;
    }

    public PersonShortShowDto getDiver() {
        return driver;
    }

    public void setDiver(PersonShortShowDto diver) {
        this.driver = diver;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public int compareTo(RoundDriverPartialResultDto o) {
        return score - o.getScore() > 0 ? 1 : -1;
    }
}
