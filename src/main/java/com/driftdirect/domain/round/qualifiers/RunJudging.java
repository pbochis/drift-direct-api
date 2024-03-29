package com.driftdirect.domain.round.qualifiers;

import com.driftdirect.domain.championship.judge.JudgeParticipation;
import com.driftdirect.domain.comment.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 12/15/2015.
 */
@Entity
@Table(name = "run_judging")
public class RunJudging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Run run;

    @ManyToOne
    private JudgeParticipation judge;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AwardedPoints> awardedPoints = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "run_judging_comment",
            joinColumns = {@JoinColumn(name = "run_judging_id")},
            inverseJoinColumns = {@JoinColumn(name = "comment_id")})
    private List<Comment> comments = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Run getRun() {
        return run;
    }

    public void setRun(Run run) {
        this.run = run;
    }

    public JudgeParticipation getJudge() {
        return judge;
    }

    public void setJudge(JudgeParticipation judge) {
        this.judge = judge;
    }

    public List<AwardedPoints> getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(List<AwardedPoints> awardedPoints) {
        this.awardedPoints.clear();
        this.awardedPoints.addAll(awardedPoints);
    }

    public void addAwardedPoints(AwardedPoints awardedPoints) {
        this.awardedPoints.add(awardedPoints);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments.clear();
        this.comments.addAll(comments);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
    public int getTotalPoints(){
        int totalPoints = 0;
        for (AwardedPoints points: this.awardedPoints){
            totalPoints += points.getAwardedPoints();
        }
        return totalPoints;
    }
}
