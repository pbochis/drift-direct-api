package com.driftdirect.domain.round.battle;

import javax.persistence.*;

/**
 * Created by Paul on 1/4/2016.
 */
@Entity
@Table(name = "battle_round")
public class BattleRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Here driver1 will be lead
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BattleRoundRun firstRun;

    //here driver2 will be lead
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BattleRoundRun secondRun;

    private boolean suddenDeath = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BattleRoundRun getFirstRun() {
        return firstRun;
    }

    public void setFirstRun(BattleRoundRun firstRun) {
        this.firstRun = firstRun;
    }

    public BattleRoundRun getSecondRun() {
        return secondRun;
    }

    public void setSecondRun(BattleRoundRun secondRun) {
        this.secondRun = secondRun;
    }

    public boolean isSuddenDeath() {
        return suddenDeath;
    }

    public void setSuddenDeath(boolean suddenDeath) {
        this.suddenDeath = suddenDeath;
    }
}
