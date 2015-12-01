package com.driftdirect.domain.championship;

import com.driftdirect.domain.person.Person;

import javax.persistence.*;

/**
 * Created by Paul on 11/29/2015.
 */
@Entity
public class ChampionshipParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Championship championship;
    @ManyToOne
    private Person person;

    @Enumerated(EnumType.STRING)
    private ChampionshipParticipationType participationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ChampionshipParticipationType getParticipationType() {
        return participationType;
    }

    public void setParticipationType(ChampionshipParticipationType participationType) {
        this.participationType = participationType;
    }
}
