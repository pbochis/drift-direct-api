package com.driftdirect.repository.round;

import com.driftdirect.domain.round.RoundScheduleEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 11/17/2015.
 */
public interface RoundScheduleRepository extends JpaRepository<RoundScheduleEntry, Long> {
}
