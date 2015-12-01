package com.driftdirect.service;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.championship.ChampionshipParticipationType;
import com.driftdirect.domain.round.Round;
import com.driftdirect.dto.championship.ChampionshipCreateDTO;
import com.driftdirect.dto.championship.ChampionshipFullDto;
import com.driftdirect.dto.championship.ChampionshipShortShowDto;
import com.driftdirect.dto.championship.ChampionshipUpdateDTO;
import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.round.RoundShortShowDto;
import com.driftdirect.exception.ObjectNotFoundException;
import com.driftdirect.mapper.ChampionshipMapper;
import com.driftdirect.mapper.PersonMapper;
import com.driftdirect.mapper.RoundMapper;
import com.driftdirect.repository.FileRepository;
import com.driftdirect.repository.championship.ChampionshipParticipationRepository;
import com.driftdirect.repository.championship.ChampionshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/6/2015.
 */

@Service
@Transactional
public class ChampionshipService{
    private ChampionshipRepository championshipRepository;
    private ChampionshipParticipationRepository championshipParticipationRepository;
    private FileRepository fileRepository;

    @Autowired
    public ChampionshipService(ChampionshipRepository championshipRepository, ChampionshipParticipationRepository championshipParticipationRepository, FileRepository fileRepository) {
        this.championshipRepository = championshipRepository;
        this.fileRepository = fileRepository;
        this.championshipParticipationRepository = championshipParticipationRepository;
    }

    public void createFromDto(ChampionshipCreateDTO dto){
        Championship c = new Championship();
        populateAndSave(c, dto);
    }

    public void update(ChampionshipUpdateDTO dto) throws ObjectNotFoundException {
        Championship c = championshipRepository.findOne(dto.getId());
        populateAndSave(c, dto);
    }

    public void delete(long id){
        championshipRepository.delete(id);
    }

    public List<RoundShortShowDto> championshipRounds(long id) {
        Championship c = championshipRepository.findOne(id);
        List<RoundShortShowDto> rounds = new ArrayList<>();
        for (Round round: c.getRounds()){
            rounds.add(RoundMapper.mapShort(round));
        }
        return rounds;
    }

    public List<ChampionshipFullDto> findChampionships() {
        List<ChampionshipFullDto> dtos = new ArrayList<>();
        for (Championship c: championshipRepository.findAll()){
            dtos.add(ChampionshipMapper.map(c));
        }
        return dtos;
    }

    public ChampionshipFullDto findChampionship(long id) {
        return ChampionshipMapper.map(championshipRepository.findOne(id));
    }

    public List<ChampionshipShortShowDto> getShortChampionshipList() {
        List<Championship> championships = championshipRepository.findAll();
        List<ChampionshipShortShowDto> dtos = new ArrayList<>();
        for (Championship c : championships) {
            dtos.add(ChampionshipMapper.mapShort(c, c.getRounds().get(0)));
        }
        return dtos;
    }

    private Championship populateAndSave(Championship c, ChampionshipCreateDTO dto){
        c.setName(dto.getName());
        c.setInformation(dto.getInformation());
        c.setPublished(dto.isPublished());
        c.setRules(dto.getRules());
        c.setTicketsUrl(dto.getTicketsUrl());
        c.setBackgroundImage(fileRepository.findOne(dto.getBackgroundImage()));
        c.setLogo(fileRepository.findOne(dto.getLogo()));
        return championshipRepository.save(c);
    }

    public List<PersonShortShowDto> findParticipations(Long championshipId, ChampionshipParticipationType type) {
        return championshipParticipationRepository.findParticipations(championshipId, type)
                .stream()
                .map(e -> PersonMapper.mapShort(e.getPerson()))
                .collect(Collectors.toList());
    }

    public List<PersonShortShowDto> getDrivers(Long id) {
        return championshipParticipationRepository.findParticipations(id, ChampionshipParticipationType.DRIVER)
                .stream()
                .map(e -> PersonMapper.mapShort(e.getPerson()))
                .collect(Collectors.toList());
    }
}
