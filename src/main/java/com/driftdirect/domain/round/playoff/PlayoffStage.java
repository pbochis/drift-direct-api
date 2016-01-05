package com.driftdirect.domain.round.playoff;

import com.driftdirect.domain.round.battle.Battle;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Paul on 1/4/2016.
 */
@Entity
@Table(name = "playoff_stage")
public class PlayoffStage implements Comparable<PlayoffStage> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @SortNatural
    private SortedSet<Battle> battles = new TreeSet<>();

    @ManyToOne
    private PlayoffTree playoffTree;

    public SortedSet<Battle> getBattles() {
        return battles;
    }

    public void setBattles(SortedSet<Battle> battles) {
        this.battles = battles;
    }

    public void addBattle(Battle battle) {
        this.battles.add(battle);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayoffTree getPlayoffTree() {
        return playoffTree;
    }

    public void setPlayoffTree(PlayoffTree playoffTree) {
        this.playoffTree = playoffTree;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PlayoffStage)) {
            return false;
        }
        return this.getBattles().size() == ((PlayoffStage) obj).getBattles().size();
    }

    @Override
    public int compareTo(PlayoffStage o) {
        if (this.battles.size() > o.getBattles().size())
            return -1;
        return 1;
    }
}
