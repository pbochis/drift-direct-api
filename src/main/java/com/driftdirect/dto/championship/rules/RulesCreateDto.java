package com.driftdirect.dto.championship.rules;

import javax.validation.constraints.NotNull;

/**
 * Created by Paul on 1/2/2016.
 */
public class RulesCreateDto {
    @NotNull
    private String rules;

    private String videoUrl;

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
