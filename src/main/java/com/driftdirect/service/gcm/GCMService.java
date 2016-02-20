package com.driftdirect.service.gcm;

import com.driftdirect.config.GCMConfig;
import com.driftdirect.domain.client.GCMClient;
import com.driftdirect.repository.client.GCMClientRepository;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 2/13/2016.
 */
@Service
@Transactional
public class GCMService {

    private GCMClientRepository gcmClientRepository;

    @Autowired
    public GCMService(GCMClientRepository gcmClientRepository) {
        this.gcmClientRepository = gcmClientRepository;
    }

    public void notifyTopic(String topic) throws IOException {
        Sender sender = new Sender(GCMConfig.API_KEY);
        Message message = new Message.Builder()
                .addData("hello", "iancu")
                .build();
        Result result = sender.sendNoRetry(message, topic);
    }

    public void registerClient(String key) {
        if (gcmClientRepository.findByKey(key) != null) {
            return;
        }
        GCMClient client = new GCMClient();
        client.setClientKey(key);
        gcmClientRepository.save(client);
    }

    public void testNotifyAllUsers() throws IOException {
        List<GCMClient> clients = gcmClientRepository.findAll();
        List<String> keys = clients.stream().map(GCMClient::getClientKey).collect(Collectors.toList());
        Sender sender = new Sender(GCMConfig.API_KEY);
        Message message = new Message.Builder()
                .addData("Hello", "World")
                .build();
        MulticastResult result = sender.send(message, keys, 1);

        //pune breakpoint aici daca nu mere
        int a = 2;
    }

    public void testNotifySingleUser(String key) throws IOException {
        Sender sender = new Sender(GCMConfig.API_KEY);
        Message message = new Message.Builder()
                .addData("Hello", "World")
                .build();
        Result result = sender.send(message, key, 1);
        //pt breakpoint
        int a = 2;
    }
}
