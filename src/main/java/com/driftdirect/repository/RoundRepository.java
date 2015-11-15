package com.driftdirect.repository;

import com.driftdirect.domain.Round;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 11/14/2015.
 */
public interface RoundRepository extends JpaRepository<Round, Long> {
}
