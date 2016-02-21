package com.driftdirect.dto.round.schedule;

/**
 * Created by Paul on 2/17/2016.
 */
public class RoundScheduleEntryUpdateDto extends RoundScheduleEntryCreateDto {
    private Long id;
    private boolean propagateUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPropagateUpdate() {
        return propagateUpdate;
    }

    public void setPropagateUpdate(boolean propagateUpdate) {
        this.propagateUpdate = propagateUpdate;
    }
}
