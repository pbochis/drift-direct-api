package com.driftdirect.domain.round.qualifiers;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 12/15/2015.
 */
@Entity
public class Run {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float entrySpeed;
    private int totalPoints;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RunJudging> judgings = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getEntrySpeed() {
        return entrySpeed;
    }

    public void setEntrySpeed(float entrySpeed) {
        this.entrySpeed = entrySpeed;
    }

    public List<RunJudging> getJudgings() {
        return judgings;
    }

    public void setJudgings(List<RunJudging> judgings) {
        this.judgings.clear();
        this.judgings.addAll(judgings);
    }

    public void addJudging(RunJudging runJudging) {
        this.judgings.add(runJudging);
    }

    public void addPoints(int points) {
        this.totalPoints += points;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
