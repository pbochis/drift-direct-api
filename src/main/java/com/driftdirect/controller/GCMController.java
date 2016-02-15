package com.driftdirect.controller;

import com.driftdirect.service.gcm.GCMService;
import com.driftdirect.util.RestUrls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by Paul on 2/15/2016.
 */
@RestController
public class GCMController {

    private GCMService gcmService;

    @Autowired
    public GCMController(GCMService gcmService) {
        this.gcmService = gcmService;
    }

    @RequestMapping(path = RestUrls.GCM_REGISTER, method = RequestMethod.POST)
    public ResponseEntity registerClient(@RequestBody String key) {
        gcmService.registerClient(key);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/gcm/test", method = RequestMethod.GET)
    public ResponseEntity testGCM(@RequestParam(name = "key", required = false) String key) throws IOException {
        if (key == null) {
            gcmService.testNotifyAllUsers();
        } else {
            gcmService.testNotifySingleUser(key);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
