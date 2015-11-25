package com.driftdirect.repository;

import com.driftdirect.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 11/23/2015.
 */
public interface CountryRepository extends JpaRepository<Country, Long> {
}
