package com.driftdirect.repository.round.qualifier;

import com.driftdirect.domain.round.qualifiers.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 12/19/2015.
 */
public interface QualifierRepository extends JpaRepository<Qualifier, Long> {
}
