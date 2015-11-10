package com.driftdirect.repository;

import com.driftdirect.domain.ConfigSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Paul on 11/10/2015.
 */
public interface ConfigSettingRepository extends JpaRepository<ConfigSetting, Long> {
    @Query("Select c From ConfigSetting c where c.key = :key")
    public ConfigSetting findByKey(@Param("key")String key);
}
