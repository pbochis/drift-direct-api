package com.driftdirect.dto.round.playoff.graphic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 1/5/2016.
 */
public class PlayoffStageGraphicDisplayDto {
    private Long id;
    private List<BattleGraphicDisplayDto> battles = new ArrayList<>();
    private int order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BattleGraphicDisplayDto> getBattles() {
        return battles;
    }

    public void setBattles(List<BattleGraphicDisplayDto> battles) {
        this.battles = battles;
    }

    public void addBattle(BattleGraphicDisplayDto battleGraphicDisplayDto) {
        this.battles.add(battleGraphicDisplayDto);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
