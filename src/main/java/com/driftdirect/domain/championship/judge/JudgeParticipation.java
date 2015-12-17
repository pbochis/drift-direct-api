package com.driftdirect.domain.championship.judge;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.person.Person;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 12/1/2015.
 */
@Entity
@Table(name = "judge_participation")
public class JudgeParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person judge;

    @Column(name = "title", nullable = true)
    @Size(min = 5, max = 25)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    private JudgeType judgeType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Size(min = 1)
    private List<PointsAllocation> pointsAllocations;

    @ManyToOne
    private Championship championship;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<PointsAllocation> getPointsAllocations() {
        return pointsAllocations;
    }

    public void setPointsAllocations(List<PointsAllocation> pointsAllocations) {
        this.pointsAllocations = pointsAllocations;
    }

    public void addPointsAllocation(PointsAllocation pointsAllocation) {
        if (this.pointsAllocations == null) {
            this.pointsAllocations = new ArrayList<>();
        }
        this.pointsAllocations.add(pointsAllocation);
    }

    public String getTitle() {
        if (this.title == null || this.title.equals("")) {
            return judgeType.getTitle();
        }
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JudgeType getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(JudgeType judgeType) {
        this.judgeType = judgeType;
    }

    public int getMaximumAllocatedPoints() {
        int points = 0;
        for (PointsAllocation point : pointsAllocations) {
            points += point.getMaxPoints();
        }
        return points;
    }
}
