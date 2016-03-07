package com.driftdirect.domain.championship;

import com.driftdirect.domain.championship.driver.DriverParticipation;
import com.driftdirect.domain.championship.judge.JudgeParticipation;
import com.driftdirect.domain.file.File;
import com.driftdirect.domain.news.ImageLink;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.sponsor.Sponsor;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Paul on 11/6/2015.
 */
@Entity
public class Championship{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    //will be a url -> where to buy ticketsUrl
    private String ticketsUrl;

    @ManyToOne(optional = false)
    private Person organizer;

    private boolean published;

    @OneToOne(fetch = FetchType.LAZY)
    private File backgroundImage;

    @OneToOne(fetch = FetchType.LAZY)
    private File logo;

    @OneToOne(cascade = CascadeType.ALL)
    private ChampionshipRules rules;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    @SortNatural
    private SortedSet<Round> rounds = new TreeSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DriverParticipation> drivers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JudgeParticipation> judges = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "championship_sponsor",
            joinColumns = {@JoinColumn(name = "championship_id")},
            inverseJoinColumns = {@JoinColumn(name = "sponsor_id")}
    )
    private Set<Sponsor> sponsors = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ImageLink> news = new HashSet<>();

    //todo: make this customizable in future versions;
    private Integer playoffSize = 32;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public SortedSet<Round> getRounds() {
        return rounds;
    }

    public void setRounds(SortedSet<Round> rounds) {
        this.rounds.clear();
        this.rounds.addAll(rounds);
    }

    public void addRound(Round round) {

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

    public List<DriverParticipation> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverParticipation> drivers) {
        this.drivers.clear();
        this.drivers.addAll(drivers);
    }

    public List<JudgeParticipation> getJudges() {
        return judges;
    }

    public void setJudges(List<JudgeParticipation> judges) {
        this.judges.clear();
        this.judges.addAll(judges);
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
        this.sponsors.clear();
        this.sponsors.addAll(sponsors);
    }

    public Set<ImageLink> getNews() {
        return news;
    }

    public void setNews(Set<ImageLink> news) {
        this.news.clear();
        this.news.addAll(news);
    }

    public void addSponsor(Sponsor sponsor) {
        if (this.sponsors == null) {
            this.sponsors = new HashSet<>();
        }
        this.sponsors.add(sponsor);
    }

    public void addNews(ImageLink news) {
        if (this.news == null) {
            this.news = new HashSet<>();
        }
        this.news.add(news);
    }

    public void addJudgeParticipation(JudgeParticipation judgeParticipation) {
        if (this.judges == null) {
            this.judges = new ArrayList<>();
        }
        this.judges.add(judgeParticipation);
    }

    public Person getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Person organizer) {
        this.organizer = organizer;
    }

    public Integer getPlayoffSize() {
        return playoffSize;
    }

    public void setPlayoffSize(Integer playoffSize) {
        this.playoffSize = playoffSize;
    }

    @Override
    public String toString() {
        return name;
    }
}
