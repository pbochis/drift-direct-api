package com.driftdirect.repository.round.playoff;

import com.driftdirect.domain.round.playoff.PlayoffStage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 1/5/2016.
 */
public interface PlayoffStageRepository extends JpaRepository<PlayoffStage, Long> {
}
