package com.driftdirect.domain;

import com.driftdirect.domain.file.File;
import com.driftdirect.domain.round.Round;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Paul on 11/6/2015.
 */
@Entity
public class Championship{
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String rules;
    private String information;
    //will be a url -> where to buy ticketsUrl
    private String ticketsUrl;
    private boolean published;

    @OneToOne(fetch = FetchType.LAZY)
    private File backgroundImage;

    @OneToOne(fetch = FetchType.LAZY)
    private File logo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Round> rounds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return name;
    }
}
