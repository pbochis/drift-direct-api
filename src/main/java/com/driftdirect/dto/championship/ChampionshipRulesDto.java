package com.driftdirect.dto.championship;

/**
 * Created by Paul on 12/5/2015.
 */
public class ChampionshipRulesDto {
    private Long id;
    private String rules;
    private String videoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
