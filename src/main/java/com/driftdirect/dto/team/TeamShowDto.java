package com.driftdirect.dto.team;

import com.driftdirect.dto.sponsor.SponsorShowDto;

import java.util.Collections;
import java.util.Set;

/**
 * Created by Paul on 11/26/2015.
 */
public class TeamShowDto{
    private Long id;
    private String name;
    private Set<SponsorShowDto> sponsors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SponsorShowDto> getSponsors() {
        return sponsors;
    }

    public void setSponsors(Set<SponsorShowDto> sponsors) {
        this.sponsors = sponsors;
    }

    public void addSponsor(SponsorShowDto dto){
        if (sponsors == null){
            sponsors = Collections.emptySet();
        }
        sponsors.add(dto);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
