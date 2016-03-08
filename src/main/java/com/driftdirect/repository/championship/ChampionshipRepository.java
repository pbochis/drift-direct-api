package com.driftdirect.repository.championship;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Paul on 11/6/2015.
 */


public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    @Query("Select c From Championship c where c.published=true")
    public List<Championship> findPublishedChampionships();

    @Query("Select c From Championship c where c.organizer=:person OR EXISTS (Select jp from JudgeParticipation jp where jp.championship=c and jp.judge=:person)")
    public List<Championship> findEditableChampionships(@Param(value = "person") Person person);

    @Query("Select c From Championship c where c.published=true OR c.organizer=:person OR EXISTS (Select jp from JudgeParticipation jp where jp.championship=c and jp.judge=:person)")
    public List<Championship> findPublicAndEditableChampionships(@Param(value = "person") Person person);
}
