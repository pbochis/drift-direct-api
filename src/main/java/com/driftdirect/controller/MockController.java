package com.driftdirect.controller;

import com.driftdirect.domain.ConfigSetting;
import com.driftdirect.domain.Country;
import com.driftdirect.domain.driver.DriverDetails;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.person.PersonType;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.dto.comment.CommentCreateDto;
import com.driftdirect.repository.*;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.playoff.BattleRepository;
import com.driftdirect.service.round.RoundService;
import com.driftdirect.service.round.playoff.PlayoffService;
import com.driftdirect.service.round.qualifier.QualifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 1/7/2016.
 */
@RestController
public class MockController {

    private final String DRIVERS_MOCKED = "driversMocked";
    List<CommentCreateDto> someComments = new ArrayList<>();
    @Autowired
    private PlayoffService playoffService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private RoundService roundService;
    @Autowired
    private BattleRepository battleRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private QualifierService qualifierService;
    @Autowired
    private ConfigSettingRepository configSettingRepository;
    @Autowired
    private DriverDetailsRepository driverDetailsRepository;
    @Autowired
    private CountryRepository countryRepository;
    private Country country;

    @Secured(Authorities.ROLE_ADMIN)
    @RequestMapping(path = "/mock/drivers", method = RequestMethod.GET)
    public ResponseEntity mockDrivers() {
        if (configSettingRepository.findByKey(DRIVERS_MOCKED) == null) {
            Round round = roundRepository.findAll().get(0);
            country = countryRepository.findAll().get(0);

            createDriver("Ryan Tuerck", 1, round);
            createDriver("Dimitri Amos", 2, round);
            createDriver("Dylan Woolhouse", 3, round);
            createDriver("Jaron Olivecrona", 4, round);
            createDriver("Ben Wilkinson", 5, round);
            createDriver("Stuart Baker", 6, round);
            createDriver("Adam Davies", 7, round);
            createDriver("Troy Jenkins", 8, round);
            createDriver("Aden Omnet", 9, round);
            createDriver("Aden Omnet", 9, round);
            createDriver("Michael Prosenik", 10, round);
            createDriver("Phil Sutherland", 11, round);
            createDriver("Brad Smith", 12, round);
            createDriver("Vincent Langhorn", 13, round);
            createDriver("Joe Kukutai", 14, round);
            createDriver("Gareth Grove", 15, round);
            createDriver("Adam Hedges", 16, round);
            createDriver("Jodie Verhulst", 17, round);
            createDriver("Tom Marshall", 18, round);
            createDriver("Shane Allen", 19, round);
            createDriver("Shayne Giles", 20, round);
            createDriver("Joe Marshall", 21, round);
            createDriver("Drew Donovan", 22, round);
            createDriver("Nico Reid", 23, round);
            createDriver("Daynom Templeman", 24, round);
            createDriver("Bruce Tannock", 25, round);
            createDriver("David Steedman", 26, round);
            createDriver("Cole Armstrong", 27, round);
            createDriver("Daniel Woolhouse", 28, round);
            createDriver("Curt Whittaker", 29, round);
            createDriver("Darren Kelly", 30, round);
            ConfigSetting configSetting = new ConfigSetting();
            configSetting.setKey(DRIVERS_MOCKED);
            configSettingRepository.save(configSetting);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    private void createDriver(String name, int qualifierOrder, Round round) {
        String firstName = name.split(" ")[0];
        String lastName = name.split(" ")[1];
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setCountry(country);
        person.setPersonType(PersonType.Driver);
        DriverDetails driverDetails = new DriverDetails();
        driverDetails = driverDetailsRepository.save(driverDetails);
        person.setDriverDetails(driverDetails);
        person = personRepository.save(person);
        qualifierService.registerDriver(round.getId(), person.getId());
    }
//    private CommentCreateDto createComment(String commentText, boolean positive) {
//        Comment comment = new Comment();
//        comment.setComment(commentText);
//        comment.setPositive(positive);
//        comment = commentRepository.save(comment);
//        CommentCreateDto dto = new CommentCreateDto();
//        dto.setId(comment.getId());
//        return dto;
//    }
//
//    private void initSomeComments() {
//        someComments.add(createComment("Good run", true));
//        someComments.add(createComment("Nice slide", true));
//
//        someComments.add(createComment("Weels off trqack", false));
//        someComments.add(createComment("Oversteered too much. He cannot drive properly", false));
//
//    }
//
//    @RequestMapping(path = "/mock/playoff/score/{id}/{stage}", method = RequestMethod.GET)
//    private void scorePlayoff(@PathVariable(value = "id") Long roundId, @PathVariable(value = "stage") int stageOrder) {
//        Round round = roundRepository.findOne(roundId);
//        List<Person> judges = round.getChampionship().getJudges().stream().map(e -> e.getJudge()).collect(Collectors.toList());
//        PlayoffTree tree = round.getPlayoffTree();
//        PlayoffStage selectedStage = null;
//        for (PlayoffStage stage : tree.getPlayoffStages()) {
//            if (stage.getOrder() == stageOrder) {
//                selectedStage = stage;
//            }
//        }
//        List<Long> battleIds = selectedStage.getBattles().stream().map(e -> e.getId()).collect(Collectors.toList());
//        for (Long battleId : battleIds) {
//            Battle battle = battleRepository.findOne(battleId);
//
//            if (battle.isAutoWin()) {
//                continue;
//            }
//            BattleRound battleRound = battle.getBattleRounds().get(0);
//            for (Person judge : judges) {
//                playoffService.submitPlayoffJudging(
//                        judge,
//                        battle.getId(),
//                        createJudging(battleRound, battleRound.getFirstRun(), battle.getDriver1(), battle.getDriver2()));
//            }
//
//            for (Person judge : judges) {
//                playoffService.submitPlayoffJudging(
//                        judge,
//                        battle.getId(),
//                        createJudging(battleRound, battleRound.getSecondRun(), battle.getDriver1(), battle.getDriver2()));
//            }
//        }
//    }
//
//    @RequestMapping(path = "/mock/playoff/{id}", method = RequestMethod.GET)
//    private PlayoffTreeGraphicDisplayDto mockPlayoffs(@PathVariable(value = "id") Long roundId) {
//        initSomeComments();
//        roundService.finishQualifiers(roundId);
//        return playoffService.generatePlayoffTree(roundId);
//    }
//
//    private PlayoffBattleRoundJudging createJudging(BattleRound round, BattleRoundRun run, QualifiedDriver driver1, QualifiedDriver driver2) {
//        PlayoffBattleRoundJudging judging = new PlayoffBattleRoundJudging();
//
//        PlayoffBattleRoundDriverJudging driver1Judging = new PlayoffBattleRoundDriverJudging();
//        driver1Judging.setPoints(6);
//        driver1Judging.setComments(someComments);
//        driver1Judging.setQualifiedDriverId(driver1.getId());
//
//        PlayoffBattleRoundDriverJudging driver2Judging = new PlayoffBattleRoundDriverJudging();
//        driver2Judging.setPoints(4);
//        driver2Judging.setComments(someComments);
//        driver2Judging.setQualifiedDriverId(driver2.getId());
//
//        judging.setDriver1(driver1Judging);
//        judging.setDriver2(driver2Judging);
//        judging.setRoundId(round.getId());
//        judging.setRunId(run.getId());
//
//        return judging;
//    }

}
