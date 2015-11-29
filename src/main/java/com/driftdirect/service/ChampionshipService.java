package com.driftdirect.service;

import com.driftdirect.domain.Championship;
import com.driftdirect.domain.round.Round;
import com.driftdirect.dto.championship.ChampionshipCreateDTO;
import com.driftdirect.dto.championship.ChampionshipShortShowDto;
import com.driftdirect.dto.championship.ChampionshipShowDto;
import com.driftdirect.dto.championship.ChampionshipUpdateDTO;
import com.driftdirect.dto.round.RoundShowDto;
import com.driftdirect.exception.ObjectNotFoundException;
import com.driftdirect.mapper.ChampionshipMapper;
import com.driftdirect.mapper.RoundMapper;
import com.driftdirect.repository.ChampionshipRepository;
import com.driftdirect.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 11/6/2015.
 */

@Service
@Transactional
public class ChampionshipService{
    private ChampionshipRepository championshipRepository;
    private FileRepository fileRepository;

    @Autowired
    public ChampionshipService(ChampionshipRepository championshipRepository, FileRepository fileRepository) {
        this.championshipRepository = championshipRepository;
        this.fileRepository = fileRepository;
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

    public List<RoundShowDto> championshipRounds(long id){
        Championship c = championshipRepository.findOne(id);
        List<RoundShowDto> rounds = new ArrayList<>();
        for (Round round: c.getRounds()){
            rounds.add(RoundMapper.map(round));
        }
        return rounds;
    }

    public List<ChampionshipShowDto> findChampionships(){
        List<ChampionshipShowDto> dtos = new ArrayList<>();
        for (Championship c: championshipRepository.findAll()){
            dtos.add(ChampionshipMapper.map(c));
        }
        return dtos;
    }

    public ChampionshipShowDto findChampionship(long id){
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
}
