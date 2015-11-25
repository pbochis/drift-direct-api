package com.driftdirect.repository;

import com.driftdirect.domain.driver.DriverDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 11/23/2015.
 */
public interface DriverDetailsRepository extends JpaRepository<DriverDetails, Long>{
}
