package com.driftdirect.repository;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.person.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Paul on 11/20/2015.
 */
public interface PersonRepository extends JpaRepository<Person, Long>{
    @Query("Select p From Person p where p.personType=:personType")
    public List<Person> findPersonsByType(@Param("personType") PersonType personType);
}
