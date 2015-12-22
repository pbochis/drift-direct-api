package com.driftdirect.repository.championship.driver;

import com.driftdirect.domain.championship.driver.DriverParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Paul on 12/1/2015.
 */
public interface DriverParticipationRepository extends JpaRepository<DriverParticipation, Long> {

    @Query("Select participation From DriverParticipation participation " +
            "JOIN participation.championship championship " +
            "JOIN participation.driver driver WHERE driver.id=:driverId and championship.id=:championshipId")
    public DriverParticipation findByChampionshipIdAndDriverId(@Param(value = "championshipId") Long championshipId,
                                                               @Param(value = "driverId") Long driverId);
}
