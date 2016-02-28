package com.driftdirect.service.migration;

import com.driftdirect.domain.ConfigSetting;
import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.championship.driver.DriverParticipation;
import com.driftdirect.domain.championship.driver.DriverParticipationResults;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.RoundDriverResult;
import com.driftdirect.repository.ConfigSettingRepository;
import com.driftdirect.repository.championship.ChampionshipRepository;
import com.driftdirect.repository.championship.driver.DriverParticipationRepository;
import com.driftdirect.repository.championship.driver.DriverParticipationResultsRepository;
import com.driftdirect.repository.round.RoundDriverResultRepository;
import com.driftdirect.service.round.RoundService;
import com.driftdirect.service.round.playoff.PlayoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Paul on 2/24/2016.
 */
@Service
@Transactional
public class D1nzResultsMigrationService {

    private final String D1NZ_RESULTS_RECALCULATED = "d1nz_recalculated";

    @Autowired
    private ConfigSettingRepository configSettingRepository;

    @Autowired
    private ChampionshipRepository championshipRepository;

    @Autowired
    private RoundDriverResultRepository roundDriverResultRepository;

    @Autowired
    private PlayoffService playoffService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private DriverParticipationResultsRepository driverParticipationResultsRepository;

    @Autowired
    private DriverParticipationRepository driverParticipationRepository;

    public void doMigration() {
        if (configSettingRepository.findByKey(D1NZ_RESULTS_RECALCULATED) != null) {
            return;
        }
        Championship d1nz = championshipRepository.findOne(4L);
        if (d1nz == null) {
            return;
        }
        //Step 1: clear championship results
        for (DriverParticipation participation : d1nz.getDrivers()) {
            DriverParticipationResults results = participation.getResults();
            if (results != null) {
                results.setTotalPoints(0F);
                driverParticipationResultsRepository.save(results);
            }
        }

        //Step 2: take each round and update round results
        for (Round round : d1nz.getRounds()) {

            for (RoundDriverResult roundResult : round.getRoundResults()) {
                //Step 2.1: set correct points for this round
                float qualifierPoints = roundService.getPointsForRanking(roundResult.getQualifierRanking(), false);
                float playoffPoints = playoffService.getPointsForRanking(roundResult.getPlayoffRanking());
                roundResult.setQualifierPoints(qualifierPoints);
                roundResult.setPlayoffPoints(playoffPoints);
                roundResult.setRoundScore(qualifierPoints + playoffPoints);
                roundDriverResultRepository.save(roundResult);
                //Step 2.2: add those points to the championship
                DriverParticipation driverParticipation = driverParticipationRepository.findByChampionshipIdAndDriverId(d1nz.getId(), roundResult.getDriver().getId());
                DriverParticipationResults champResults = driverParticipation.getResults();
                champResults.addPoints(roundResult.getRoundScore());
                driverParticipationResultsRepository.save(champResults);
            }
        }

        ConfigSetting configSetting = new ConfigSetting();
        configSetting.setKey(D1NZ_RESULTS_RECALCULATED);
        configSettingRepository.save(configSetting);
    }

}
