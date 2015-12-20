package com.driftdirect.domain.round.track;

import com.driftdirect.domain.file.File;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Paul on 12/13/2015.
 */
@Entity
@Table(name = "track_layout")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String description;
    private String videoUrl;
    @NotNull
    private String judgingCriteria;

    @OneToOne
    private File layout;

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

    public File getLayout() {
        return layout;
    }

    public void setLayout(File layout) {
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
