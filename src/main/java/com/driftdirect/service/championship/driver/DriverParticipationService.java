package com.driftdirect.service.championship.driver;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.championship.driver.DriverParticipation;
import com.driftdirect.domain.championship.driver.DriverParticipationResults;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.RoundDriverResult;
import com.driftdirect.repository.championship.driver.DriverParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Paul on 12/20/2015.
 */
@Service
@Transactional
public class DriverParticipationService {
    private DriverParticipationRepository driverParticipationRepository;

    @Autowired
    public DriverParticipationService(DriverParticipationRepository driverParticipationRepository){
        this.driverParticipationRepository = driverParticipationRepository;
    }

    public void addDriverParticipation(Championship championship, Person driver){
        DriverParticipation participation = driverParticipationRepository.findByChampionshipIdAndDriverId(championship.getId(), driver.getId());
        if (participation == null){
            participation = new DriverParticipation();
            participation.setChampionship(championship);
            participation.setDriver(driver);
            driverParticipationRepository.save(participation);
        }
    }

    public void addResult(Championship championship, RoundDriverResult roundResult) {
        DriverParticipation driverParticipation = driverParticipationRepository.findByChampionshipIdAndDriverId(championship.getId(), roundResult.getDriver().getId());
        DriverParticipationResults results = driverParticipation.getResults();
        if (results == null) {
            results = new DriverParticipationResults();
            driverParticipation.setResults(results);
        }
        results.addPoints(roundResult.getPlayoffPoints());
        results.addPoints(roundResult.getQualifierPoints());
        driverParticipationRepository.save(driverParticipation);
    }

}
