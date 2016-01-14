package com.driftdirect.domain.round.qualifiers;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;

import javax.persistence.*;

/**
 * Created by Paul on 12/15/2015.
 */
@Entity
public class Qualifier implements Comparable<Qualifier> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Person driver;
    @OneToOne(cascade = CascadeType.ALL)
    private Run firstRun;
    @OneToOne(cascade = CascadeType.ALL)
    private Run secondRun;

    private float finalScore;
    private int qualifierOrder;

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getDriver() {
        return driver;
    }

    public void setDriver(Person driver) {
        this.driver = driver;
    }

    public Run getFirstRun() {
        return firstRun;
    }

    public void setFirstRun(Run firstRun) {
        this.firstRun = firstRun;
    }

    public Run getSecondRun() {
        return secondRun;
    }

    public void setSecondRun(Run secondRun) {
        this.secondRun = secondRun;
    }

    public float getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(float finalScore) {
        this.finalScore = finalScore;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    @Override
    public int compareTo(Qualifier o) {
        return this.getQualifierOrder() - o.getQualifierOrder();
//        if (this.id.equals(o.getId())) {
//            return 0;
//        }
//        if (this.finalScore > o.getFinalScore()) {
//            return 1;
//        }
//        if (this.finalScore < o.getFinalScore()) {
//            return -1;
//        }
//        int a = Math.min(firstRun.getTotalPoints(), secondRun.getTotalPoints());
//        int b = Math.min(o.getFirstRun().getTotalPoints(), o.getSecondRun().getTotalPoints());
//
//        if (a > b) {
//            return 1;
//        }
//        if (a < b) {
//            return -1;
//        }
//        return 0;
    }

    public int getQualifierOrder() {
        return qualifierOrder;
    }

    public void setQualifierOrder(int qualifierOrder) {
        this.qualifierOrder = qualifierOrder;
    }
}
