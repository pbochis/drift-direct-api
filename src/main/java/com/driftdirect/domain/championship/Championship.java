package com.driftdirect.domain.championship;

import com.driftdirect.domain.file.File;
import com.driftdirect.domain.news.News;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.sponsor.Sponsor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Paul on 11/6/2015.
 */
@Entity
public class Championship{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String information;
    //will be a url -> where to buy ticketsUrl
    private String ticketsUrl;
    private boolean published;

    @OneToOne(fetch = FetchType.LAZY)
    private File backgroundImage;

    @OneToOne(fetch = FetchType.LAZY)
    private File logo;

    @OneToOne(cascade = CascadeType.ALL)
    private ChampionshipRules rules;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Round> rounds;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChampionshipDriverParticipation> drivers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChampionshipJudgeParticipation> judges;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChampionshipJudgeType> judgeTypes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "championship_sponsor",
            joinColumns = {@JoinColumn(name = "championship_id")},
            inverseJoinColumns = {@JoinColumn(name = "sponsor_id")}
    )
    private Set<Sponsor> sponsors;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<News> news;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public File getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(File backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    public List<ChampionshipDriverParticipation> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<ChampionshipDriverParticipation> drivers) {
        this.drivers = drivers;
    }

    public List<ChampionshipJudgeParticipation> getJudges() {
        return judges;
    }

    public void setJudges(List<ChampionshipJudgeParticipation> judges) {
        this.judges = judges;
    }

    public List<ChampionshipJudgeType> getJudgeTypes() {
        return judgeTypes;
    }

    public void setJudgeTypes(List<ChampionshipJudgeType> judgeTypes) {
        this.judgeTypes = judgeTypes;
    }

    public ChampionshipRules getRules() {
        return rules;
    }

    public void setRules(ChampionshipRules rules) {
        this.rules = rules;
    }

    public Set<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(Set<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }

    public void addSponsor(Sponsor sponsor) {
        if (this.sponsors == null) {
            this.sponsors = new HashSet<>();
        }
        this.sponsors.add(sponsor);
    }

    public void addNews(News news) {
        if (this.news == null) {
            this.news = new HashSet<>();
        }
        this.news.add(news);
    }

    @Override
    public String toString() {
        return name;
    }
}
