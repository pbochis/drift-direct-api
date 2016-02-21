package com.driftdirect.service.round;

import com.driftdirect.service.gcm.GCMService;
import com.driftdirect.service.gcm.TopicBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Paul on 2/20/2016.
 */
@Service
public class RoundNotificationService {
    private TopicBuilder topicBuilder;
    private GCMService gcmService;

    @Autowired
    public RoundNotificationService(TopicBuilder topicBuilder, GCMService gcmService) {
        this.topicBuilder = topicBuilder;
        this.gcmService = gcmService;
    }

    public void notifyCurrentQualifierUpdated(Long roundId) throws IOException {
        String topic = topicBuilder.buildQualificationsTopic(roundId);
        gcmService.notifyTopic(topic);
    }

    public void notifyCurrentBattleUpdated(Long roundId) throws IOException {
        String topic = topicBuilder.buildBattlesTopic(roundId);
        gcmService.notifyTopic(topic);
    }
}
