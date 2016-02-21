package com.driftdirect.service.gcm;

import org.springframework.stereotype.Component;

/**
 * Created by Paul on 2/20/2016.
 */
@Component
public class TopicBuilder {

    private final String TOPICS = "/topics/";

    public String buildQualificationsTopic(Long roundId) {
        return TOPICS + Scope.ROUND + "-" + roundId + "-" + Event.QUALIFICATIONS;
    }

    public String buildBattlesTopic(Long roundId) {
        return TOPICS + Scope.ROUND + "-" + roundId + "-" + Event.BATTLES;
    }

    private class Scope {
        public static final String ROUND = "round";
    }

    private class Event {
        public static final String QUALIFICATIONS = "qualifications";
        public static final String BATTLES = "battles";
    }
}
