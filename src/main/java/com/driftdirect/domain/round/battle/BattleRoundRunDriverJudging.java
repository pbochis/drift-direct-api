package com.driftdirect.domain.round.battle;

import com.driftdirect.domain.comment.Comment;
import com.driftdirect.domain.person.Person;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Paul on 1/4/2016.
 */
@Entity
@Table(name = "battle_round_run_driver_judging")
public class BattleRoundRunDriverJudging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person judge;

    private Integer points;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "battle_round_run_judging_comment",
            joinColumns = {@JoinColumn(name = "battle_round_run_judging_id")},
            inverseJoinColumns = {@JoinColumn(name = "comment_id")})
    private List<Comment> comments;

    public Person getJudge() {
        return judge;
    }

    public void setJudge(Person judge) {
        this.judge = judge;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
