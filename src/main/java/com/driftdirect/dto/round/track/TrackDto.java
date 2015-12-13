package com.driftdirect.dto.round.track;

/**
 * Created by Paul on 12/13/2015.
 */
public class TrackDto {
    private Long id;
    private String description;
    private String videoUrl;
    private String judgingCriteria;
    private Long layout;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
