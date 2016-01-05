package com.driftdirect.mapper.round.playoff;

import com.driftdirect.domain.round.battle.Battle;
import com.driftdirect.domain.round.playoff.PlayoffStage;
import com.driftdirect.domain.round.playoff.PlayoffTree;
import com.driftdirect.dto.round.playoff.graphic.BattleGraphicDisplayDto;
import com.driftdirect.dto.round.playoff.graphic.PlayoffStageGraphicDisplayDto;
import com.driftdirect.dto.round.playoff.graphic.PlayoffTreeGraphicDisplayDto;
import com.driftdirect.mapper.round.qualifier.QualifierMapper;

/**
 * Created by Paul on 1/5/2016.
 */
public class PlayoffMapper {
    public static PlayoffTreeGraphicDisplayDto mapPlayoffForDisplay(PlayoffTree tree) {
        if (tree == null) {
            return null;
        }
        PlayoffTreeGraphicDisplayDto dto = new PlayoffTreeGraphicDisplayDto();
        dto.setId(tree.getId());
        for (PlayoffStage stage : tree.getPlayoffStages()) {
            dto.addStage(mapStageForDisplay(stage));
        }
        return dto;
    }

    private static PlayoffStageGraphicDisplayDto mapStageForDisplay(PlayoffStage stage) {
        PlayoffStageGraphicDisplayDto dto = new PlayoffStageGraphicDisplayDto();
        dto.setId(stage.getId());
        for (Battle battle : stage.getBattles()) {
            dto.addBattle(mapBattle(battle));
        }
        return dto;
    }

    private static BattleGraphicDisplayDto mapBattle(Battle battle) {
        BattleGraphicDisplayDto dto = new BattleGraphicDisplayDto();
        dto.setId(battle.getId());
        dto.setDriver1(QualifierMapper.mapQualifiedDriver(battle.getDriver1()));
        dto.setDriver2(QualifierMapper.mapQualifiedDriver(battle.getDriver2()));
        dto.setOrder(battle.getOrder());
        //TODO add more stuff here
        return dto;
    }


}
