package com.driftdirect.repository;

import com.driftdirect.domain.sponsor.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 11/26/2015.
 */
public interface SponsorRepository extends JpaRepository<Sponsor, Long>{
}
