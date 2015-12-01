package com.driftdirect.repository.championship;

import com.driftdirect.domain.championship.ChampionshipParticipation;
import com.driftdirect.domain.championship.ChampionshipParticipationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Paul on 11/29/2015.
 */
public interface ChampionshipParticipationRepository extends JpaRepository<ChampionshipParticipation, Long> {
    @Query("Select cp From ChampionshipParticipation cp " +
            "JOIN cp.championship c " +
            "where c.id=:championshipId and cp.participationType=:participationType")
    public List<ChampionshipParticipation> findParticipations(@Param("championshipId") Long championshipId,
                                                              @Param("participationType") ChampionshipParticipationType participationType);
}
