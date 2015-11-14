package com.driftdirect.service;

import com.driftdirect.domain.Championship;
import com.driftdirect.dto.championship.ChampionshipCreateDTO;
import com.driftdirect.dto.championship.ChampionshipUpdateDTO;
import com.driftdirect.exception.ObjectNotFoundException;
import com.driftdirect.repository.ChampionshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Paul on 11/6/2015.
 */

@Service
public class ChampionshipService extends AbstractBaseService<ChampionshipRepository, Championship>{

    @Autowired
    public ChampionshipService(ChampionshipRepository repository){
        super(repository);
    }

    @Transactional
    public Championship createFromDto(ChampionshipCreateDTO dto){
        Championship c = new Championship();
        return populateAndSave(c, dto);
    }

    @Transactional
    public Championship update(ChampionshipUpdateDTO dto) throws ObjectNotFoundException {
        Championship c = findById(dto.getId());
        if (c == null){
            throw new ObjectNotFoundException("championship not found");
        }
        return populateAndSave(c, dto);
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
