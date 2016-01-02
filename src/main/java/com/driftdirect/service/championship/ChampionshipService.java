package com.driftdirect.service.championship;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.championship.ChampionshipRules;
import com.driftdirect.domain.championship.driver.DriverParticipation;
import com.driftdirect.domain.news.News;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.championship.ChampionshipCreateDTO;
import com.driftdirect.dto.championship.ChampionshipFullDto;
import com.driftdirect.dto.championship.ChampionshipShortShowDto;
import com.driftdirect.dto.championship.driver.DriverParticipationDto;
import com.driftdirect.dto.championship.judge.JudgeParticipationCreateDto;
import com.driftdirect.dto.championship.judge.JudgeParticipationDto;
import com.driftdirect.dto.news.NewsCreateDto;
import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.mapper.ChampionshipMapper;
import com.driftdirect.mapper.PersonMapper;
import com.driftdirect.repository.FileRepository;
import com.driftdirect.repository.championship.ChampionshipRepository;
import com.driftdirect.repository.championship.driver.DriverParticipationRepository;
import com.driftdirect.repository.championship.judge.JudgeParticipationRepository;
import com.driftdirect.service.championship.judge.JudgeParticipationService;
import com.driftdirect.service.round.RoundService;
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
    private DriverParticipationRepository driverParticipationRepository;
    private JudgeParticipationRepository judgeParticipationRepository;
    private FileRepository fileRepository;
    private RoundService roundService;
    private JudgeParticipationService judgeParticipationService;

    @Autowired
    public ChampionshipService(ChampionshipRepository championshipRepository,
                               DriverParticipationRepository driverParticipationRepository,
                               JudgeParticipationRepository judgeParticipationRepository,
                               FileRepository fileRepository, RoundService roundService,
                               JudgeParticipationService judgeParticipationService) {
        this.championshipRepository = championshipRepository;
        this.roundService = roundService;
        this.fileRepository = fileRepository;
        this.driverParticipationRepository = driverParticipationRepository;
        this.judgeParticipationRepository = judgeParticipationRepository;
        this.judgeParticipationService = judgeParticipationService;
    }

    public void createFromDto(ChampionshipCreateDTO dto, User currentUser) throws Exception {
        Championship c = new Championship();
        c.setOrganizer(currentUser.getPerson());
        populateAndSave(c, dto);
    }

//    public void update(ChampionshipUpdateDTO dto) throws Exception {
//        Championship c = championshipRepository.findOne(dto.getId());
//        populateAndSave(c, dto);
//    }

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

    private Championship populateAndSave(Championship c, ChampionshipCreateDTO dto) throws Exception {
        c.setName(dto.getName());
        c.setTicketsUrl(dto.getTicketsUrl());
        c.setBackgroundImage(fileRepository.findOne(dto.getBackgroundImage()));
        c.setLogo(fileRepository.findOne(dto.getLogo()));
        ChampionshipRules rules = new ChampionshipRules();
        rules.setVideoUrl(dto.getRules().getVideoUrl());
        rules.setRules(dto.getRules().getRules());
        c.setRules(rules);
        c = championshipRepository.save(c);
        for (RoundCreateDto roundCreateDto : dto.getRounds()) {
            roundService.createFromDto(c, roundCreateDto);
        }
        for (JudgeParticipationCreateDto judgeParticipationCreateDto : dto.getJudges()) {
            judgeParticipationService.addJudge(c, judgeParticipationCreateDto);
        }
        return championshipRepository.save(c);
    }

    public List<PersonShortShowDto> findDrivers(Long championshipId) {
        Championship championship = championshipRepository.findOne(championshipId);
        return championship.getDrivers()
                .stream()
                .map(e -> PersonMapper.mapShort(e.getDriver()))
                .collect(Collectors.toList());
    }

    public DriverParticipationDto findDriver(Long championshipId, Long driverId) {
        DriverParticipation part = driverParticipationRepository.findByChampionshipIdAndDriverId(championshipId, driverId);
        return ChampionshipMapper.mapDriverParticipation(part);
    }

    public List<JudgeParticipationDto> findJudges(Long championshipId) {
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

    public boolean publish(Long championshipId) {
        Championship championship = championshipRepository.findOne(championshipId);
        if (championship.getJudges().size() != 3) {
            return false;
        }
        if (championship.getRounds().size() < 1) {
            return false;
        }
        //TODO: add more conditions for this
        championship.setPublished(true);
        championshipRepository.save(championship);
        return true;
    }
}
