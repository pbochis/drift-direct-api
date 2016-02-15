package com.driftdirect.domain.client;

import javax.persistence.*;

/**
 * Created by Paul on 2/15/2016.
 */
@Entity
@Table(name = "gcm_client")
public class GCMClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
