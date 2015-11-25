package com.driftdirect.service;

import com.driftdirect.domain.Championship;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.RoundScheduleEntry;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.dto.round.RoundShowDto;
import com.driftdirect.dto.round.RoundScheduleCreateDto;
import com.driftdirect.dto.round.RoundUpdateDto;
import com.driftdirect.mapper.RoundMapper;
import com.driftdirect.repository.ChampionshipRepository;
import com.driftdirect.repository.RoundRepository;
import com.driftdirect.repository.RoundScheduleRepository;
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

    @Autowired
    public RoundService(RoundRepository roundRepository, ChampionshipRepository championshipRepository, RoundScheduleRepository roundScheduleRepository){
        this.roundRepository = roundRepository;
        this.championshipRepository = championshipRepository;
        this.roundScheduleRepository = roundScheduleRepository;
    }

    public Round createFromDto(RoundCreateDto dto){
        Championship championship = championshipRepository.findOne(dto.getChampionshipId());
        Round round = new Round();
        round.setName(dto.getName());
        round.setChampionship(championship);
        return roundRepository.save(round);
    }

    public RoundShowDto update(RoundUpdateDto dto) {
        Round round = roundRepository.findOne(dto.getId());
        Championship championship = championshipRepository.findOne(dto.getChampionshipId());
        round.setChampionship(championship);
        round.setName(dto.getName());
        return RoundMapper.map(roundRepository.save(round));
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
