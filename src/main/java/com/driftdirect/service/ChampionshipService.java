package com.driftdirect.service;

import com.driftdirect.domain.Championship;
import com.driftdirect.repository.ChampionshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Paul on 11/6/2015.
 */

@Service
@Transactional
public class ChampionshipService extends AbstractBaseService<ChampionshipRepository, Championship>{

    @Autowired
    public ChampionshipService(ChampionshipRepository repository){
        super(repository);
    }

}
