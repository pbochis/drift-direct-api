package com.driftdirect.repository.round.playoff;

import com.driftdirect.domain.round.battle.Battle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 1/5/2016.
 */
public interface BattleRepository extends JpaRepository<Battle, Long> {
}
