package com.driftdirect.mapper;

import com.driftdirect.domain.driver.Team;
import com.driftdirect.domain.sponsor.Sponsor;
import com.driftdirect.dto.team.TeamShowDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/27/2015.
*/
public class TeamMapper {
    public static TeamShowDto map(Team team){
        TeamShowDto dto = new TeamShowDto();
        dto.setName(team.getName());
        dto.setId(team.getId());
        for (Sponsor sponsor: team.getSponsors()){
            dto.addSponsor(SponsorMapper.map(sponsor));
        }
        return dto;
    }

    public static List<TeamShowDto> map(List<Team> teams){
        return teams.stream().map(TeamMapper::map).collect(Collectors.toList());
    }
}
