package com.driftdirect.repository.round.qualifier;

import com.driftdirect.domain.round.qualifiers.QualifiedDriver;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 1/5/2016.
 */
public interface QualifiedDriverRepository extends JpaRepository<QualifiedDriver, Long> {
}
