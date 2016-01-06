package com.driftdirect.dto.round.playoff.battle;

import com.driftdirect.domain.round.qualifiers.QualifiedDriver;
import com.driftdirect.dto.round.qualifier.QualifiedDriverDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 1/6/2016.
 */
public class PlayoffBattleFullDto {

    private Long id;
    private QualifiedDriverDto driver1;
    private QualifiedDriverDto driver2;
    private QualifiedDriverDto winner;

    private List<PlayoffBattleRoundFullDto> rounds = new ArrayList<>();

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

    public QualifiedDriverDto getWinner() {
        return winner;
    }

    public void setWinner(QualifiedDriverDto winner) {
        this.winner = winner;
    }

    public List<PlayoffBattleRoundFullDto> getRounds() {
        return rounds;
    }

    public void setRounds(List<PlayoffBattleRoundFullDto> rounds) {
        this.rounds = rounds;
    }

    public void addRound(PlayoffBattleRoundFullDto round){
        this.rounds.add(round);
    }
}
