package com.driftdirect.dto.round.playoff;

import com.driftdirect.domain.round.qualifiers.QualifiedDriver;
import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.round.qualifier.QualifiedDriverDto;

/**
 * Created by Paul on 1/5/2016.
 */
public class PlayoffBattleDriverToJudgeDto {
    private QualifiedDriverDto driver;
    private boolean lead;
    private boolean advantage;

    public QualifiedDriverDto getDriver() {
        return driver;
    }

    public void setDriver(QualifiedDriverDto driver) {
        this.driver = driver;
    }

    public boolean isLead() {
        return lead;
    }

    public void setLead(boolean lead) {
        this.lead = lead;
    }

    public boolean isAdvantage() {
        return advantage;
    }

    public void setAdvantage(boolean advantage) {
        this.advantage = advantage;
    }
}
