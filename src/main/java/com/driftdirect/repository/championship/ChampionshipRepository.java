package com.driftdirect.repository.championship;

import com.driftdirect.domain.championship.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Paul on 11/6/2015.
 */


public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    @Query("Select c From Championship c where c.published=true")
    public List<Championship> findPublishedChampionships();
}
