package com.driftdirect.service.round;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.RoundScheduleEntry;
import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.domain.round.track.Track;
import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.dto.round.RoundScheduleEntryCreateDto;
import com.driftdirect.dto.round.RoundShowDto;
import com.driftdirect.dto.round.track.TrackCreateDto;
import com.driftdirect.mapper.PersonMapper;
import com.driftdirect.mapper.round.RoundMapper;
import com.driftdirect.repository.FileRepository;
import com.driftdirect.repository.championship.ChampionshipRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.RoundScheduleRepository;
import com.driftdirect.repository.round.track.TrackLayoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/14/2015.
 */
@Transactional
@Service
public class RoundService {
    private RoundRepository roundRepository;
    private ChampionshipRepository championshipRepository;
    private RoundScheduleRepository roundScheduleRepository;
    private TrackLayoutRepository trackLayoutRepository;
    private FileRepository fileRepository;

    @Autowired
    public RoundService(RoundRepository roundRepository, FileRepository fileRepository, ChampionshipRepository championshipRepository, RoundScheduleRepository roundScheduleRepository, TrackLayoutRepository trackLayoutRepository) {
        this.roundRepository = roundRepository;
        this.fileRepository = fileRepository;
        this.championshipRepository = championshipRepository;
        this.roundScheduleRepository = roundScheduleRepository;
        this.trackLayoutRepository = trackLayoutRepository;
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
}
