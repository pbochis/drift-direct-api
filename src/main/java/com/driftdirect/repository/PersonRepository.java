package com.driftdirect.repository;

import com.driftdirect.domain.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 11/20/2015.
 */
public interface PersonRepository extends JpaRepository<Person, Long>{
}
