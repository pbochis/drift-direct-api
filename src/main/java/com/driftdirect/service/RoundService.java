package com.driftdirect.service;

import com.driftdirect.domain.Championship;
import com.driftdirect.domain.Round;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.dto.round.RoundDto;
import com.driftdirect.dto.round.RoundUpdateDto;
import com.driftdirect.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

/**
 * Created by Paul on 11/14/2015.
 */
@Service
public class RoundService extends AbstractBaseService<RoundRepository, Round> {


    @Autowired
    public RoundService(RoundRepository repository){
        super(repository);
    }

    @Transactional
    public RoundDto createFromDto(RoundCreateDto dto){
        Round round = new Round();
        round.setName(dto.getName());
        round.setChampionship(dto.getChampionship());
        return convertToDto(repository.save(round));
    }

    @Transactional
    public RoundDto update(RoundUpdateDto dto) throws NoSuchElementException {
        Round round = findById(dto.getId());
        if (round == null){
            throw new NoSuchElementException("championship not found");
        }
        round.setChampionship(dto.getChampionship());
        round.setName(dto.getName());
        return convertToDto(repository.save(round));
    }

    public RoundDto findRound(long id){
        return convertToDto(findById(id));
    }

    public RoundDto convertToDto(Round round){
        RoundDto dto = new RoundDto();
        dto.setName(round.getName());
        dto.setId(round.getId());
        dto.setStartDate(round.getStartDate());
        dto.setEndDate(round.getEndDate());
        return dto;
    }
}
