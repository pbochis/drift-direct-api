package com.driftdirect.domain.round.battle;

import javax.persistence.*;

/**
 * Created by Paul on 1/4/2016.
 */
@Entity
@Table(name = "battle_round_run")
public class BattleRoundRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // if 'this' is the first run, driver1 is lead
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BattleRoundRunDriver driver1;

    // if 'this' is the second run, driver2 is lead
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BattleRoundRunDriver driver2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BattleRoundRunDriver getDriver1() {
        return driver1;
    }

    public void setDriver1(BattleRoundRunDriver driver1) {
        this.driver1 = driver1;
    }

    public BattleRoundRunDriver getDriver2() {
        return driver2;
    }

    public void setDriver2(BattleRoundRunDriver driver2) {
        this.driver2 = driver2;
    }
}
