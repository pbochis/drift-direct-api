package com.driftdirect.dto.round.playoff.graphic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 1/5/2016.
 */
public class PlayoffTreeGraphicDisplayDto {
    private Long id;
    private List<PlayoffStageGraphicDisplayDto> stages = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PlayoffStageGraphicDisplayDto> getStages() {
        return stages;
    }

    public void setStages(List<PlayoffStageGraphicDisplayDto> stages) {
        this.stages = stages;
    }

    public void addStage(PlayoffStageGraphicDisplayDto stage) {
        this.stages.add(stage);
    }
}
