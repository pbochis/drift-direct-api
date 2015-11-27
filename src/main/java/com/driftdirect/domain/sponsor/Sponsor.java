package com.driftdirect.domain.sponsor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Paul on 11/26/2015.
 */
@Entity
public class Sponsor {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;
    private String telephoneNr;

    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNr() {
        return telephoneNr;
    }

    public void setTelephoneNr(String telephoneNr) {
        this.telephoneNr = telephoneNr;
    }
}
