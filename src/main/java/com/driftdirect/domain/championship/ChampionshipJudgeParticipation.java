package com.driftdirect.domain.championship;

import com.driftdirect.domain.person.Person;

import javax.persistence.*;

/**
 * Created by Paul on 12/1/2015.
 */
@Entity
@Table(name = "championship_judge_participation")
public class ChampionshipJudgeParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person judge;

    @ManyToOne
    private Championship championship;

    @OneToOne
    private ChampionshipJudgeType judgeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChampionshipJudgeType getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(ChampionshipJudgeType judgeType) {
        this.judgeType = judgeType;
    }

    public Person getJudge() {
        return judge;
    }

    public void setJudge(Person judge) {
        this.judge = judge;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }
}
