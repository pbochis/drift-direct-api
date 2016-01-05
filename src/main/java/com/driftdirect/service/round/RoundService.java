package com.driftdirect.service.round;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
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
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.RoundScheduleRepository;
import com.driftdirect.repository.round.qualifier.QualifiedDriverRepository;
import com.driftdirect.service.championship.driver.DriverParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    private DriverParticipationService driverParticipationService;
    private QualifiedDriverRepository qualifiedDriverRepository;

    @Autowired
    public RoundService(DriverParticipationService driverParticipationService,
                        RoundRepository roundRepository,
                        FileRepository fileRepository,
                        RoundScheduleRepository roundScheduleRepository,
                        QualifiedDriverRepository qualifiedDriverRepository) {
        this.roundRepository = roundRepository;
        this.fileRepository = fileRepository;
        this.roundScheduleRepository = roundScheduleRepository;
        this.driverParticipationService = driverParticipationService;
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

    public void finishQualifiers(Long roundId) {
        Round round = roundRepository.findOne(roundId);
        List<Qualifier> qualifiers = round.getQualifiers();
        Collections.sort(qualifiers);
        List<Qualifier> topQualifiers = qualifiers.subList(0, 24);
        Championship championship = round.getChampionship();
        List<QualifiedDriver> qualifiedDrivers = new ArrayList<>();
        for (int i = 0; i < championship.getPlayoffSize(); i++) {
            Qualifier driver = topQualifiers.get(i);
            int place = i + 1;
            QualifiedDriver qualifiedDriver = new QualifiedDriver();
            qualifiedDriver.setDriver(driver.getDriver());
            qualifiedDriver.setRanking(place);
            qualifiedDriver.setRound(round);
            qualifiedDriverRepository.save(qualifiedDriver);
            if (place == 1) driverParticipationService.addResult(championship, driver.getDriver(), 12);
            if (place == 2) driverParticipationService.addResult(championship, driver.getDriver(), 10);
            if (place == 3) driverParticipationService.addResult(championship, driver.getDriver(), 8);
            if (place == 4) driverParticipationService.addResult(championship, driver.getDriver(), 6);
            if (place == 5 || place == 6) driverParticipationService.addResult(championship, driver.getDriver(), 4);
            if (place == 7 || place == 8) driverParticipationService.addResult(championship, driver.getDriver(), 3);
            if (place >= 9 && place <= 12) driverParticipationService.addResult(championship, driver.getDriver(), 2);
            if (place >= 13 && place <= 16) driverParticipationService.addResult(championship, driver.getDriver(), 1);
            if (place >= 17 && place <= 24)
                driverParticipationService.addResult(championship, driver.getDriver(), (float) 0.5);
        }
    }

    public PlayoffTreeGraphicDisplayDto getPlayoffs(Long roundId) {
        Round round = roundRepository.findOne(roundId);
        return PlayoffMapper.mapPlayoffForDisplay(round.getPlayoffTree());
    }
}
