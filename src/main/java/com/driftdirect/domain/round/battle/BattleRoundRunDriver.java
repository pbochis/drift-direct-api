package com.driftdirect.domain.round.battle;

import com.driftdirect.domain.round.qualifiers.QualifiedDriver;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Paul on 1/4/2016.
 */
@Entity
@Table(name = "battle_round_run_driver")
public class BattleRoundRunDriver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private QualifiedDriver driver;

    private Integer points;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<BattleRoundRunDriverJudging> judgings = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QualifiedDriver getDriver() {
        return driver;
    }

    public void setDriver(QualifiedDriver driver) {
        this.driver = driver;
    }

    public Set<BattleRoundRunDriverJudging> getJudgings() {
        return judgings;
    }

    public void setJudgings(Set<BattleRoundRunDriverJudging> judgings) {
        this.judgings = judgings;
    }

    public void addJudging(BattleRoundRunDriverJudging judging) {
        this.judgings.add(judging);
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void addPoints(Integer points) {
        if (this.points == null) {
            this.points = points;
        } else {
            this.points += points;
        }
    }
}
