package com.driftdirect.domain.news;

import com.driftdirect.domain.file.File;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Paul on 12/6/2015.
 */
@Entity
@Table(name = "News")
public class ImageLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    private String description;
    @NotNull
    private String url;

    @OneToOne
    private File logo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
