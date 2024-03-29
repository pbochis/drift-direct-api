package com.driftdirect.repository.round;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.RoundDriverResult;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Paul on 1/7/2016.
 */
public interface RoundDriverResultRepository extends JpaRepository<RoundDriverResult, Long> {
    @Query("Select roundResult From RoundDriverResult roundResult where roundResult.round=:round AND roundResult.driver=:driver")
    public RoundDriverResult findByRoundAndDriver(@Param("round") Round round, @Param("driver") Person driver);

    @Query("Select roundResult " +
            "From RoundDriverResult roundResult " +
            "INNER JOIN roundResult.round round " +
            "where :startDate >= round.endDate")
    public List<RoundDriverResult> findResultsBeforeDate(@Param("startDate") DateTime date);
}
