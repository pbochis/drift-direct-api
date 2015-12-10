package com.driftdirect.service;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.news.News;
import com.driftdirect.dto.championship.*;
import com.driftdirect.dto.news.NewsCreateDto;
import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.exception.ObjectNotFoundException;
import com.driftdirect.mapper.ChampionshipMapper;
import com.driftdirect.mapper.PersonMapper;
import com.driftdirect.repository.FileRepository;
import com.driftdirect.repository.championship.ChampionshipDriverParticipationRepository;
import com.driftdirect.repository.championship.ChampionshipJudgeParticipationRepository;
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
    private ChampionshipDriverParticipationRepository driverParticipationRepository;
    private ChampionshipJudgeParticipationRepository judgeParticipationRepository;
    private FileRepository fileRepository;

    @Autowired
    public ChampionshipService(ChampionshipRepository championshipRepository,
                               ChampionshipDriverParticipationRepository driverParticipationRepository,
                               ChampionshipJudgeParticipationRepository judgeParticipationRepository,
                               FileRepository fileRepository) {
        this.championshipRepository = championshipRepository;
        this.fileRepository = fileRepository;
        this.driverParticipationRepository = driverParticipationRepository;
        this.judgeParticipationRepository = judgeParticipationRepository;
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
        return championships
                .stream()
                .map(ChampionshipMapper::mapShort)
                .collect(Collectors.toList());
    }

    private Championship populateAndSave(Championship c, ChampionshipCreateDTO dto){
        c.setName(dto.getName());
        c.setInformation(dto.getInformation());
        c.setPublished(dto.isPublished());
        c.setTicketsUrl(dto.getTicketsUrl());
        c.setBackgroundImage(fileRepository.findOne(dto.getBackgroundImage()));
        c.setLogo(fileRepository.findOne(dto.getLogo()));
        return championshipRepository.save(c);
    }

    public List<PersonShortShowDto> findDrivers(Long championshipId) {
        Championship championship = championshipRepository.findOne(championshipId);
        return championship.getDrivers()
                .stream()
                .map(e -> PersonMapper.mapShort(e.getDriver()))
                .collect(Collectors.toList());
    }

    public ChampionshipDriverParticipationDto findDriver(Long championshipId, Long driverId) {
        return ChampionshipMapper.mapDriverParticipation(driverParticipationRepository.findByChampionshipIdAndDriverId(championshipId, driverId));
    }

    public List<ChampionshipJudgeParticipationDto> findJudges(Long championshipId) {
        Championship c = championshipRepository.findOne(championshipId);
        return c.getJudges()
                .stream()
                .map(ChampionshipMapper::mapJudgeParticipation)
                .collect(Collectors.toList());
    }

    public void addNews(Long championshipId, NewsCreateDto newsDto) {
        News news = new News();
        news.setName(newsDto.getName());
        news.setDescription(newsDto.getDescrption());
        news.setUrl(newsDto.getUrl());
        news.setLogo(fileRepository.findOne(newsDto.getLogo()));
        Championship c = championshipRepository.findOne(championshipId);
        c.addNews(news);
        championshipRepository.save(c);
    }
}
