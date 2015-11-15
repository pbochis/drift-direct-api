package com.driftdirect.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Paul on 11/6/2015.
 */
@Entity
public class Championship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String rules;
    private String information;
    //will be a url -> where to buy ticketsUrl
    private String ticketsUrl;

    private boolean published;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Round> rounds;

    public long getId() {
        return id;
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

    public Set<Round> getRounds() {
        return rounds;
    }

    @Override
    public String toString() {
        return name;
    }
}
