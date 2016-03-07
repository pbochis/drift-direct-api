package com.driftdirect.dto.news;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Paul on 12/6/2015.
 */
public class ImageLinkCreateDto {
    @NotNull
    private String name;

    private String description;

    @NotNull
    @Size(min = 5)
    private String url;
    @NotNull
    private Long logo;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }
}
