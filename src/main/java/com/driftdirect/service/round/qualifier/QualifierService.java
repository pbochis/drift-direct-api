package com.driftdirect.service.round.qualifier;

import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.dto.round.qualifier.QualifierFullDto;
import com.driftdirect.mapper.round.qualifier.QualifierMapper;
import com.driftdirect.repository.PersonRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.qualifier.QualifierRepository;
import com.driftdirect.service.championship.driver.DriverParticipationService;
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
    private DriverParticipationService driverParticipationService;

    @Autowired
    public QualifierService(PersonRepository personRepository, RoundRepository roundRepository, QualifierRepository qualifierRepository, DriverParticipationService driverParticipationService) {
        this.personRepository = personRepository;
        this.roundRepository = roundRepository;
        this.qualifierRepository = qualifierRepository;
        this.driverParticipationService = driverParticipationService;
    }

    public void registerDriver(Long roundId, Long driverId) {
        Round round = roundRepository.findOne(roundId);
        Person driver = personRepository.findOne(driverId);
        Qualifier qualifier = new Qualifier();
        qualifier.setDriver(driver);
        qualifier.setRound(round);
        qualifierRepository.save(qualifier);
        driverParticipationService.addDriverParticipation(round.getChampionship(), driver);
    }

    public QualifierFullDto findQualifier(Long id){
        return QualifierMapper.mapFull(qualifierRepository.findOne(id));
    }
}
