package com.driftdirect.repository.round;

import com.driftdirect.domain.round.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Paul on 11/14/2015.
 */
public interface RoundRepository extends JpaRepository<Round, Long> {

    @Query("select round from Round round " +
            "join fetch round.championship championship " +
            "where round.id=:roundId")
    public Round findOneWithChampionship(@Param(value = "roundId") Long roundId);
}
