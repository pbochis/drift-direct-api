package com.driftdirect.dto.news;

import javax.validation.constraints.NotNull;

/**
 * Created by Paul on 12/6/2015.
 */
public class NewsCreateDto {
    @NotNull
    private String name;
    private String descrption;
    private String url;
    private Long logo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
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
