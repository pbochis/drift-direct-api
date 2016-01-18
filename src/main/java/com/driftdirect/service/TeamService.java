package com.driftdirect.service;

import com.driftdirect.domain.driver.Team;
import com.driftdirect.dto.team.TeamCreateDto;
import com.driftdirect.dto.team.TeamShowDto;
import com.driftdirect.dto.team.TeamUpdateDto;
import com.driftdirect.mapper.TeamMapper;
import com.driftdirect.repository.SponsorRepository;
import com.driftdirect.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Paul on 11/27/2015.
 */
@Service
@Transactional
public class TeamService {
    private TeamRepository teamRepository;
    private SponsorRepository sponsorRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, SponsorRepository sponsorRepository){
        this.teamRepository = teamRepository;
        this.sponsorRepository = sponsorRepository;
    }

    public void createFromDto(TeamCreateDto dto){
        Team team = new Team();
        team.setName(dto.getName());
        teamRepository.save(team);
    }

    public void update(TeamUpdateDto dto){
        Team team = teamRepository.findOne(dto.getId());
        team.setName(dto.getName());
        teamRepository.save(team);
    }

    public List<TeamShowDto> findAll(){
        return TeamMapper.map(teamRepository.findAll());
    }

    public TeamShowDto findById(Long id){
        return TeamMapper.map(teamRepository.findOne(id));
    }

    public void delete(Long id){
        teamRepository.delete(id);
    }
}
