package com.driftdirect.repository.client;

import com.driftdirect.domain.client.GCMClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Paul on 2/15/2016.
 */
public interface GCMClientRepository extends JpaRepository<GCMClient, Long> {
    @Query("Select c From GCMCLient c where c.key = :key")
    public GCMClient findByKey(@Param("key") String key);
}
