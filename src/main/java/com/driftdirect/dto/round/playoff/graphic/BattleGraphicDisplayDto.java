package com.driftdirect.dto.round.playoff.graphic;

import com.driftdirect.dto.round.qualifier.QualifiedDriverDto;

/**
 * Created by Paul on 1/5/2016.
 */
public class BattleGraphicDisplayDto {
    private Long id;
    private QualifiedDriverDto driver1;
    private QualifiedDriverDto driver2;
    private QualifiedDriverDto winner;
    private int runsCompleted;
    private int order;

    //TODO: add other fields here

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QualifiedDriverDto getDriver1() {
        return driver1;
    }

    public void setDriver1(QualifiedDriverDto driver1) {
        this.driver1 = driver1;
    }

    public QualifiedDriverDto getDriver2() {
        return driver2;
    }

    public void setDriver2(QualifiedDriverDto driver2) {
        this.driver2 = driver2;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public QualifiedDriverDto getWinner() {
        return winner;
    }

    public void setWinner(QualifiedDriverDto winner) {
        this.winner = winner;
    }

    public int getRunsCompleted() {
        return runsCompleted;
    }

    public void setRunsCompleted(int runsCompleted) {
        this.runsCompleted = runsCompleted;
    }
}
