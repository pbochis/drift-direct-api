package com.driftdirect.dto.championship;

import com.driftdirect.dto.championship.judge.JudgeParticipationDto;
import com.driftdirect.dto.news.NewsShowDto;
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
    private String ticketsUrl;

    private boolean published;
    private PersonShortShowDto organizer;

    private Long backgroundImage;
    private Long logo;

    private List<RoundShortShowDto> rounds;
    private List<PersonShortShowDto> drivers;
    private List<JudgeParticipationDto> judges;
    private List<SponsorShowDto> sponsors;
    private List<NewsShowDto> news;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public ChampionshipRulesDto getRules() {
        return rules;
    }

    public void setRules(ChampionshipRulesDto rules) {
        this.rules = rules;
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

    public List<JudgeParticipationDto> getJudges() {
        return judges;
    }

    public void setJudges(List<JudgeParticipationDto> judges) {
        this.judges = judges;
    }

    public List<SponsorShowDto> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<SponsorShowDto> sponsors) {
        this.sponsors = sponsors;
    }

    public List<NewsShowDto> getNews() {
        return news;
    }

    public void setNews(List<NewsShowDto> news) {
        this.news = news;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public PersonShortShowDto getOrganizer() {
        return organizer;
    }

    public void setOrganizer(PersonShortShowDto organizer) {
        this.organizer = organizer;
    }
}
