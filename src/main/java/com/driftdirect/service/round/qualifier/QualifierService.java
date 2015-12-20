package com.driftdirect.service.round.qualifier;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.repository.PersonRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.qualifier.QualifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Paul on 12/19/2015.
 */
@Service
@Transactional
public class QualifierService {
    private PersonRepository personRepository;
    private RoundRepository roundRepository;
    private QualifierRepository qualifierRepository;

    @Autowired
    public QualifierService(PersonRepository personRepository, RoundRepository roundRepository, QualifierRepository qualifierRepository) {
        this.personRepository = personRepository;
        this.roundRepository = roundRepository;
        this.qualifierRepository = qualifierRepository;
    }

    public void registerDriver(Long roundId, Long driverId) {
        Round round = roundRepository.findOne(roundId);
        Person driver = personRepository.findOne(driverId);
        Qualifier qualifier = new Qualifier();
        qualifier.setDriver(driver);
        qualifier.setRound(round);
        qualifierRepository.save(qualifier);
    }
}
