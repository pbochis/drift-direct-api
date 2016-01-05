package com.driftdirect.domain.round.playoff;

import com.driftdirect.domain.round.Round;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Paul on 1/4/2016.
 */
@Entity
public class PlayoffTree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playoffTree")
    @SortNatural
    private SortedSet<PlayoffStage> playoffStages = new TreeSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    private Round round;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SortedSet<PlayoffStage> getPlayoffStages() {
        return playoffStages;
    }

    public void setPlayoffStages(SortedSet<PlayoffStage> playoffStages) {
        this.playoffStages = playoffStages;
    }

    public void addStage(PlayoffStage stage) {
        this.playoffStages.add(stage);
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }
}
