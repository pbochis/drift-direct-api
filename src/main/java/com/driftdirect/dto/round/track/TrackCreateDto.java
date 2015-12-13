package com.driftdirect.dto.round.track;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Paul on 12/13/2015.
 */
public class TrackCreateDto {
    @NotNull
    @Size(min = 5)
    private String description;

    @NotNull
    private String judgingCriteria;
    @NotNull
    private Long layout;

    private String videoUrl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLayout() {
        return layout;
    }

    public void setLayout(Long layout) {
        this.layout = layout;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getJudgingCriteria() {
        return judgingCriteria;
    }

    public void setJudgingCriteria(String judgingCriteria) {
        this.judgingCriteria = judgingCriteria;
    }
}
