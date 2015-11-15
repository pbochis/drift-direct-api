package com.driftdirect.dto.championship;

import com.driftdirect.dto.round.RoundDto;

import java.util.List;

/**
 * Created by Paul on 11/15/2015.
 */
public class ChampionshipDto {

    private long id;
    private String name;
    private String rules;
    private String information;
    private String ticketsUrl;

    private List<RoundDto> rounds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getTicketsUrl() {
        return ticketsUrl;
    }

    public void setTicketsUrl(String ticketsUrl) {
        this.ticketsUrl = ticketsUrl;
    }

    public List<RoundDto> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundDto> rounds) {
        this.rounds = rounds;
    }
}
