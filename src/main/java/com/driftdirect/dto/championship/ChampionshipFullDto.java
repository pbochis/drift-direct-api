package com.driftdirect.dto.championship;

import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.round.RoundShortShowDto;
import com.driftdirect.dto.sponsor.SponsorShowDto;

import java.util.List;

/**
 * Created by Paul on 11/15/2015.
 */
public class ChampionshipFullDto {
    private Long id;
    private String name;
    private ChampionshipRulesDto rules;
    private String information;
    private String ticketsUrl;

    private Long backgroundImage;
    private Long logo;

    private List<RoundShortShowDto> rounds;
    private List<PersonShortShowDto> drivers;
    private List<ChampionshipJudgeParticipationDto> judges;
    private List<SponsorShowDto> sponsors;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ChampionshipRulesDto getRules() {
        return rules;
    }

    public void setRules(ChampionshipRulesDto rules) {
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

    public List<RoundShortShowDto> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundShortShowDto> rounds) {
        this.rounds = rounds;
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

    public List<PersonShortShowDto> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<PersonShortShowDto> drivers) {
        this.drivers = drivers;
    }

    public List<ChampionshipJudgeParticipationDto> getJudges() {
        return judges;
    }

    public void setJudges(List<ChampionshipJudgeParticipationDto> judges) {
        this.judges = judges;
    }

    public List<SponsorShowDto> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<SponsorShowDto> sponsors) {
        this.sponsors = sponsors;
    }
}
