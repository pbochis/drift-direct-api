package com.driftdirect.service;

import com.driftdirect.domain.Championship;
import com.driftdirect.domain.Round;
import com.driftdirect.dto.championship.ChampionshipCreateDTO;
import com.driftdirect.dto.championship.ChampionshipDto;
import com.driftdirect.dto.championship.ChampionshipUpdateDTO;
import com.driftdirect.dto.round.RoundDto;
import com.driftdirect.exception.ObjectNotFoundException;
import com.driftdirect.repository.ChampionshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Paul on 11/6/2015.
 */

@Service
public class ChampionshipService extends AbstractBaseService<ChampionshipRepository, Championship>{

    private RoundService roundService;

    @Autowired
    public ChampionshipService(ChampionshipRepository repository, RoundService roundService){
        super(repository);
        this.roundService = roundService;
    }

    @Transactional
    public ChampionshipDto createFromDto(ChampionshipCreateDTO dto){
        Championship c = new Championship();
        return convertToDto(populateAndSave(c, dto));
    }

    @Transactional
    public ChampionshipDto update(ChampionshipUpdateDTO dto) throws ObjectNotFoundException {
        Championship c = findById(dto.getId());
        if (c == null){
            throw new ObjectNotFoundException("championship not found");
        }
        return convertToDto(populateAndSave(c, dto));
    }

    public List<RoundDto> championshipRounds(long id){
        Championship c = findById(id);
        List<RoundDto> rounds = new ArrayList<>();
        for (Round round: c.getRounds()){
            rounds.add(roundService.convertToDto(round));
        }
        return rounds;
    }

    public List<ChampionshipDto> findChampionships(){
        List<ChampionshipDto> dtos = new ArrayList<>();
        for (Championship c: repository.findAll()){
            dtos.add(convertToDto(c));
        }
        return dtos;
    }

    @Transactional
    public ChampionshipDto findChampionship(long id){
        return convertToDto(findById(id));
    }

    public ChampionshipDto convertToDto(Championship c){
        ChampionshipDto dto = new ChampionshipDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setRules(c.getRules());
        dto.setInformation(c.getInformation());
        dto.setTicketsUrl(dto.getTicketsUrl());
        List<RoundDto> rounds = new ArrayList<>();
        for (Round round: c.getRounds()){
            rounds.add(roundService.convertToDto(round));
        }
        dto.setRounds(rounds);
        return dto;
    }

    private Championship populateAndSave(Championship c, ChampionshipCreateDTO dto){
        c.setName(dto.getName());
        c.setInformation(dto.getInformation());
        c.setPublished(dto.isPublished());
        c.setRules(dto.getRules());
        c.setTicketsUrl(dto.getTicketsUrl());
        return save(c);
    }
}
