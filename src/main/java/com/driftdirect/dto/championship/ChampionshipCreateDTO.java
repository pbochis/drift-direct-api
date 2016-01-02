package com.driftdirect.dto.championship;

import com.driftdirect.dto.championship.judge.JudgeParticipationCreateDto;
import com.driftdirect.dto.championship.rules.RulesCreateDto;
import com.driftdirect.dto.round.RoundCreateDto;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Paul on 11/11/2015.
 */
public class ChampionshipCreateDTO {
    List<RoundCreateDto> rounds;
    List<JudgeParticipationCreateDto> judges;
    //TODO: add List<ChampionshipJudgeTypeCreateDto> to this
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String ticketsUrl;
    private Long logo;
    private Long backgroundImage;
    private RulesCreateDto rules;

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    public Long getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Long backgroundImage) {
        this.backgroundImage = backgroundImage;
    }


    public String getTicketsUrl() {
        return ticketsUrl;
    }

    public void setTicketsUrl(String ticketsUrl) {
        this.ticketsUrl = ticketsUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RulesCreateDto getRules() {
        return rules;
    }

    public void setRules(RulesCreateDto rules) {
        this.rules = rules;
    }

    public List<RoundCreateDto> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundCreateDto> rounds) {
        this.rounds = rounds;
    }

    public List<JudgeParticipationCreateDto> getJudges() {
        return judges;
    }

    public void setJudges(List<JudgeParticipationCreateDto> judges) {
        this.judges = judges;
    }
}
