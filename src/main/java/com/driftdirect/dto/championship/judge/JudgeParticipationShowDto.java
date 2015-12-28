package com.driftdirect.dto.championship.judge;

import com.driftdirect.domain.championship.judge.JudgeType;
import com.driftdirect.dto.person.PersonShortShowDto;

import java.util.List;

/**
 * Created by Paul on 12/1/2015.
 */
public class JudgeParticipationShowDto extends JudgeParticipationDto{
    private PersonShortShowDto judge;

    public PersonShortShowDto getJudge() {
        return judge;
    }

    public void setJudge(PersonShortShowDto judge) {
        this.judge = judge;
    }
}
