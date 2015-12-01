package com.driftdirect.repository.championship;

import com.driftdirect.domain.championship.Championship;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 11/6/2015.
 */


public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
}
