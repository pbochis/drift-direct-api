package com.driftdirect.domain.round.battle;

import com.driftdirect.domain.round.playoff.PlayoffStage;
import com.driftdirect.domain.round.qualifiers.QualifiedDriver;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 1/4/2016.
 */
@Entity
public class Battle implements Comparable<Battle> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "battle_order")
    private int order;

    // Driver 1 will always be the one with higher score in qualifiers.
    // That means that in every battleRound, in battleRounRun number one, driver1 will be LEAD
    // and in battleRoundRun number two, driver1 will be chase.

    @OneToOne
    private QualifiedDriver driver1;

    @OneToOne
    private QualifiedDriver driver2;

    private boolean autoWin = false;

    @ManyToOne
    private PlayoffStage playoffStage;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BattleRound> battleRounds = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QualifiedDriver getDriver1() {
        return driver1;
    }

    public void setDriver1(QualifiedDriver driver1) {
        this.driver1 = driver1;
    }

    public QualifiedDriver getDriver2() {
        return driver2;
    }

    public void setDriver2(QualifiedDriver driver2) {
        this.driver2 = driver2;
    }

    public List<BattleRound> getBattleRounds() {
        return battleRounds;
    }

    public void setBattleRounds(List<BattleRound> battleRounds) {
        this.battleRounds = battleRounds;
    }

    public void addBattleRound(BattleRound battleRound) {
        this.battleRounds.add(battleRound);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isAutoWin() {
        return autoWin;
    }

    public void setAutoWin(boolean autoWin) {
        this.autoWin = autoWin;
    }

    public PlayoffStage getPlayoffStage() {
        return playoffStage;
    }

    public void setPlayoffStage(PlayoffStage playoffStage) {
        this.playoffStage = playoffStage;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Battle)) {
            return false;
        }
        return order == ((Battle) obj).getOrder();
    }

    @Override
    public int compareTo(Battle o) {
        return order - o.getOrder();
    }
}
