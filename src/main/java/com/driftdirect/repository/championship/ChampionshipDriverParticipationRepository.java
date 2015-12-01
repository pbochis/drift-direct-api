package com.driftdirect.repository.championship;

import com.driftdirect.domain.championship.ChampionshipDriverParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Paul on 12/1/2015.
 */
public interface ChampionshipDriverParticipationRepository extends JpaRepository<ChampionshipDriverParticipation, Long> {

    @Query("Select p From ChampionshipDriverParticipation p " +
            "JOIN p.championship c " +
            "JOIN p.driver d WHERE d.id=:driverId and c.id=:championshipId")
    public ChampionshipDriverParticipation findByChampionshipIdAndDriverId(@Param(value = "championshipId") Long championshipId,
                                                                           @Param(value = "driverId") Long driverId);
}
