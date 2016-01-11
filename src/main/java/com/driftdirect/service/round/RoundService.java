package com.driftdirect.service.round;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.RoundDriverResult;
import com.driftdirect.domain.round.RoundScheduleEntry;
import com.driftdirect.domain.round.qualifiers.QualifiedDriver;
import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.domain.round.track.Track;
import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.dto.round.RoundScheduleEntryCreateDto;
import com.driftdirect.dto.round.RoundShowDto;
import com.driftdirect.dto.round.playoff.graphic.PlayoffTreeGraphicDisplayDto;
import com.driftdirect.dto.round.track.TrackCreateDto;
import com.driftdirect.mapper.PersonMapper;
import com.driftdirect.mapper.round.RoundMapper;
import com.driftdirect.mapper.round.playoff.PlayoffMapper;
import com.driftdirect.repository.FileRepository;
import com.driftdirect.repository.round.RoundDriverResultRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.RoundScheduleRepository;
import com.driftdirect.repository.round.qualifier.QualifiedDriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/14/2015.
 */
@Transactional
@Service
public class RoundService {
    private RoundRepository roundRepository;
    private RoundScheduleRepository roundScheduleRepository;
    private FileRepository fileRepository;
    private RoundDriverResultRepository roundDriverResultRepository;
    private QualifiedDriverRepository qualifiedDriverRepository;

    @Autowired
    public RoundService(RoundDriverResultRepository roundDriverResultRepository,
                        RoundRepository roundRepository,
                        FileRepository fileRepository,
                        RoundScheduleRepository roundScheduleRepository,
                        QualifiedDriverRepository qualifiedDriverRepository) {
        this.roundRepository = roundRepository;
        this.fileRepository = fileRepository;
        this.roundScheduleRepository = roundScheduleRepository;
        this.roundDriverResultRepository = roundDriverResultRepository;
        this.qualifiedDriverRepository = qualifiedDriverRepository;
    }

    public Round createFromDto(Championship c, RoundCreateDto dto) {
        Round round = new Round();
        round.setChampionship(c);
        round.setName(dto.getName());
        round.setLogo(fileRepository.findOne(dto.getLogo()));
        round.setTicketsUrl(dto.getTicketsUrl());
        round.setTrack(createTrack(dto.getTrack()));
        round = roundRepository.save(round);
        for (RoundScheduleEntryCreateDto schedule : dto.getScheduele()) {
            addRoundSchedule(round, schedule);
        }
        return round;
    }

    public Track createTrack(TrackCreateDto trackCreateDto) {
        Track track = new Track();
        track.setDescription(trackCreateDto.getDescription());
        track.setLayout(fileRepository.findOne(trackCreateDto.getLayout()));
        track.setVideoUrl(trackCreateDto.getVideoUrl());
        track.setJudgingCriteria(trackCreateDto.getJudgingCriteria());
        return track;
    }

//    public RoundShowDto update(RoundUpdateDto dto) {
//        Round round = roundRepository.findOne(dto.getId());
//        Championship championship = championshipRepository.findOne(dto.getChampionshipId());
//        round.setChampionship(championship);
//        round.setName(dto.getName());
//        round.setLogo(fileRepository.findOne(dto.getLogo()));
//        round.setStartDate(dto.getStartDate());
//        round.setEndDate(dto.getEndDate());
//        round.setTicketsUrl(dto.getTicketsUrl());
//        return RoundMapper.map(roundRepository.save(round));
//    }

    public void addTrack(Round round, TrackCreateDto trackCreateDto) {
        Track track = new Track();
        track.setDescription(trackCreateDto.getDescription());
        track.setLayout(fileRepository.findOne(trackCreateDto.getLayout()));
        track.setVideoUrl(trackCreateDto.getVideoUrl());
        track.setJudgingCriteria(trackCreateDto.getJudgingCriteria());
        round.setTrack(track);
        roundRepository.save(round);
    }

    public void addRoundSchedule(Round round, RoundScheduleEntryCreateDto dto) {
        RoundScheduleEntry roundScheduleEntry = new RoundScheduleEntry();
        roundScheduleEntry.setName(dto.getName());
        roundScheduleEntry.setStartDate(dto.getStartDate());
        roundScheduleEntry.setEndDate(dto.getEndDate());
        roundScheduleEntry.setRound(round);
        roundScheduleRepository.save(roundScheduleEntry);
        if (round.getStartDate() == null && round.getEndDate() == null) {
            round.setStartDate(dto.getStartDate());
            round.setEndDate(dto.getEndDate());
        } else if (round.getStartDate() != null && round.getStartDate().isAfter(dto.getStartDate())) {
            round.setStartDate(dto.getStartDate());
        } else if (round.getEndDate() != null && round.getEndDate().isBefore(dto.getEndDate())) {
            round.setEndDate(dto.getEndDate());
        }
        roundRepository.save(round);
    }

    public void delete(Long id){
        roundRepository.delete(id);
    }

    public List<PersonShortShowDto> getPersonsForRegisterDesk(Long roundId){
        Round round = roundRepository.findOne(roundId);
        List<Person> drivers = round.getChampionship().getDrivers().stream().map(e -> e.getDriver()).collect(Collectors.toList());
        for (Qualifier qualifier: round.getQualifiers()){
            drivers.remove(qualifier.getDriver());
        }
        return drivers.stream().map(PersonMapper::mapShort).collect(Collectors.toList());
    }

    public RoundShowDto findRound(long id){
        return RoundMapper.map(roundRepository.findOne(id));
    }

    public boolean finishQualifiers(Long roundId) {
        Round round = roundRepository.findOne(roundId);
        if (round.getQualifiers().size() == 0) return false;
        for (Qualifier qualifier : round.getQualifiers()) {
            if (qualifier.getFirstRun().getJudgings().size() != 3 || qualifier.getSecondRun().getJudgings().size() != 3) {
                return false;
            }
        }
        List<Qualifier> qualifiers = round.getQualifiers();
        Collections.sort(qualifiers);
        Championship championship = round.getChampionship();
        for (int i = 0; i < qualifiers.size(); i++) {
            Qualifier driver = qualifiers.get(i);
            int place = i + 1;
            if (place <= championship.getPlayoffSize()) {
                createQualifiedDriver(driver.getDriver(), place, round);
            }
            createRoundResult(driver.getDriver(), place, round);
        }
        return true;
    }

    private void createRoundResult(Person driver, int place, Round round) {
        RoundDriverResult roundResult = new RoundDriverResult();
        roundResult.setDriver(driver);
        roundResult.setQualifierRanking(place);
        roundResult.setRound(round);
        if (place == 1) roundResult.setQualifierPoints(12);
        else if (place == 2) roundResult.setQualifierPoints(10);
        else if (place == 3) roundResult.setQualifierPoints(8);
        else if (place == 4) roundResult.setQualifierPoints(6);
        else if (place == 5 || place == 6) roundResult.setQualifierPoints(4);
        else if (place == 7 || place == 8) roundResult.setQualifierPoints(3);
        else if (place >= 9 && place <= 12) roundResult.setQualifierPoints(2);
        else if (place >= 13 && place <= 16) roundResult.setQualifierPoints(1);
        else if (place >= 17 && place <= 24) roundResult.setQualifierPoints(0.5F);
        else roundResult.setQualifierPoints(0F);
        roundDriverResultRepository.save(roundResult);
    }

    private void createQualifiedDriver(Person driver, int ranking, Round round) {
        QualifiedDriver qualifiedDriver = new QualifiedDriver();
        qualifiedDriver.setDriver(driver);
        qualifiedDriver.setRanking(ranking);
        qualifiedDriver.setRound(round);
        qualifiedDriverRepository.save(qualifiedDriver);

    }

    public PlayoffTreeGraphicDisplayDto getPlayoffs(Long roundId) {
        Round round = roundRepository.findOne(roundId);
        return PlayoffMapper.mapPlayoffForDisplay(round.getPlayoffTree());
    }
}
