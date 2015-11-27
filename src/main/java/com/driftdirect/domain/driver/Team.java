package com.driftdirect.domain.driver;

import com.driftdirect.domain.sponsor.Sponsor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Paul on 11/20/2015.
 */
@Entity
public class Team {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "team_sponsor",
            joinColumns = {@JoinColumn(name = "team_id")},
            inverseJoinColumns = {@JoinColumn(name = "sponsor_id")}
    )
    Set<Sponsor> sponsors;

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

    public Set<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(Set<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public void addSponsor(Sponsor sponsor){
        if (sponsors == null){
            sponsors = new HashSet<>();
        }
        sponsors.add(sponsor);
    }
}
