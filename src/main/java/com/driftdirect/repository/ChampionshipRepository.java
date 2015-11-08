package com.driftdirect.repository;

import com.driftdirect.domain.Championship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Paul on 11/6/2015.
 */


public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
}
