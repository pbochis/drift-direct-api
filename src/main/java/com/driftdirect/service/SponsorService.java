package com.driftdirect.service;

import com.driftdirect.domain.sponsor.Sponsor;
import com.driftdirect.dto.sponsor.SponsorCreateDto;
import com.driftdirect.dto.sponsor.SponsorShowDto;
import com.driftdirect.dto.sponsor.SponsorUpdateDto;
import com.driftdirect.mapper.SponsorMapper;
import com.driftdirect.repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Paul on 11/26/2015.
 */
@Service
@Transactional
public class SponsorService {
    private SponsorRepository sponsorRepository;

    @Autowired
    public SponsorService(SponsorRepository sponsorRepository){
        this.sponsorRepository = sponsorRepository;
    }

    public void createFromDto(SponsorCreateDto dto){
        Sponsor sponsor = new Sponsor();
        sponsor.setName(dto.getName());
        sponsor.setEmail(dto.getEmail());
        sponsor.setTelephoneNr(dto.getTelephoneNr());
        sponsor.setUrl(dto.getUrl());
        sponsorRepository.save(sponsor);
    }

    public void update(SponsorUpdateDto dto){
        Sponsor sponsor = sponsorRepository.findOne(dto.getId());
        sponsor.setName(dto.getName());
        sponsor.setTelephoneNr(dto.getTelephoneNr());
        sponsor.setEmail(dto.getEmail());
        sponsor.setUrl(dto.getUrl());
        sponsorRepository.save(sponsor);
    }

    public List<SponsorShowDto> findAll(){
        return SponsorMapper.map(sponsorRepository.findAll());
    }

    public SponsorShowDto findById(Long id){
        return SponsorMapper.map(sponsorRepository.findOne(id));
    }

    public void delete(Long id){
        sponsorRepository.delete(id);
    }
}
