package com.driftdirect.service.migration;

import com.driftdirect.domain.ConfigSetting;
import com.driftdirect.domain.round.battle.Battle;
import com.driftdirect.repository.ConfigSettingRepository;
import com.driftdirect.repository.round.playoff.BattleRepository;
import com.driftdirect.service.round.playoff.PlayoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Paul on 2/28/2016.
 */
@Service
@Transactional
public class D1nzMissingBattleMigrationService {

    private final String MISSING_BATTLE_MIGRATION = "d1nz_missing_battle";

    @Autowired
    private ConfigSettingRepository configSettingRepository;

    @Autowired
    private PlayoffService playoffService;

    @Autowired
    private BattleRepository battleRepository;

    public void doMigration() {
        if (configSettingRepository.findByKey(MISSING_BATTLE_MIGRATION) != null) {
            return;
        }
        Battle battle = battleRepository.findOne(323L);
        if (battle == null) {
            return;
        }
        playoffService.moveWinnerUp(battle);
        ConfigSetting configSetting = new ConfigSetting();
        configSetting.setKey(MISSING_BATTLE_MIGRATION);
        configSettingRepository.save(configSetting);
    }
}
