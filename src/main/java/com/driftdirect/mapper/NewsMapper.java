package com.driftdirect.mapper;

import com.driftdirect.domain.news.News;
import com.driftdirect.dto.news.NewsShowDto;

/**
 * Created by Paul on 12/6/2015.
 */
public class NewsMapper {

    public static NewsShowDto map(News news) {
        NewsShowDto dto = new NewsShowDto();
        dto.setId(news.getId());
        dto.setLogo(news.getLogo().getId());
        dto.setName(news.getName());
        dto.setDescription(news.getDescription());
        dto.setUrl(news.getUrl());
        return dto;
    }
}
