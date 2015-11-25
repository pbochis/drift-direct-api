package com.driftdirect.domain.driver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Paul on 11/20/2015.
 */
@Entity
public class Team {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    //TODO: add sponsors to team

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
}
