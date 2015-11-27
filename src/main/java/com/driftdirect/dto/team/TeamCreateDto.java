package com.driftdirect.dto.team;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Paul on 11/26/2015.
 */
public class TeamCreateDto {
    @NotNull
    @Length(min = 3, max = 50)
    private String name;

    private Set<Long> sponsors = Collections.emptySet();

    public String getName() {
        return name;
    }

    public Set<Long> getSponsors() {
        return sponsors;
    }

    public void setSponsors(Set<Long> sponsors) {
        this.sponsors = sponsors;
    }

    public void setName(String name) {
        this.name = name;
    }
}
