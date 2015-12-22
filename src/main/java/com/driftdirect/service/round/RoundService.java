package com.driftdirect.service.round;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.RoundScheduleEntry;
import com.driftdirect.domain.round.track.Track;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.dto.round.RoundScheduleCreateDto;
import com.driftdirect.dto.round.RoundShowDto;
import com.driftdirect.dto.round.RoundUpdateDto;
import com.driftdirect.dto.round.track.TrackCreateDto;
import com.driftdirect.mapper.round.RoundMapper;
import com.driftdirect.repository.FileRepository;
import com.driftdirect.repository.championship.ChampionshipRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.RoundScheduleRepository;
import com.driftdirect.repository.round.track.TrackLayoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public Long createFromDto(RoundCreateDto dto) {
        Championship championship = championshipRepository.findOne(dto.getChampionshipId());
        Round round = new Round();
        round.setName(dto.getName());
        round.setChampionship(championship);
        round.setLogo(fileRepository.findOne(dto.getLogo()));
        round.setStartDate(dto.getStartDate());
        round.setEndDate(dto.getEndDate());
        round.setTicketsUrl(dto.getTicketsUrl());
        return roundRepository.save(round).getId();
    }

    public RoundShowDto update(RoundUpdateDto dto) {
        Round round = roundRepository.findOne(dto.getId());
        Championship championship = championshipRepository.findOne(dto.getChampionshipId());
        round.setChampionship(championship);
        round.setName(dto.getName());
        round.setLogo(fileRepository.findOne(dto.getLogo()));
        round.setStartDate(dto.getStartDate());
        round.setEndDate(dto.getEndDate());
        round.setTicketsUrl(dto.getTicketsUrl());
        return RoundMapper.map(roundRepository.save(round));
    }

    public void addTrack(Long id, TrackCreateDto trackCreateDto){
        Round round = roundRepository.findOne(id);
        Track track = new Track();
        track.setDescription(trackCreateDto.getDescription());
        track.setLayout(fileRepository.findOne(trackCreateDto.getLayout()));
        track.setVideoUrl(trackCreateDto.getVideoUrl());
        track.setJudgingCriteria(trackCreateDto.getJudgingCriteria());
        track = trackLayoutRepository.save(track);
        round.setTrack(track);
        roundRepository.save(round);
    }

    public void addRoundSchedule(Long roundId, RoundScheduleCreateDto dto) {
        Round round = roundRepository.findOne(roundId);
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

    public RoundShowDto findRound(long id){
        return RoundMapper.map(roundRepository.findOne(id));
    }
}
