package com.driftdirect.repository.round.qualifier;

import com.driftdirect.domain.round.qualifiers.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Paul on 12/19/2015.
 */
public interface QualifierRepository extends JpaRepository<Qualifier, Long> {

    @Query("Select qualifier from Qualifier qualifier " +
            "inner join fetch qualifier.round round " +
            "where qualifier.id=:qualifierId")
    Qualifier findOneWithRound(@Param(value = "qualifierId") Long qualifierId);

}
