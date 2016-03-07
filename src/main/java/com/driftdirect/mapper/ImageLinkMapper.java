package com.driftdirect.mapper;

import com.driftdirect.domain.news.ImageLink;
import com.driftdirect.dto.news.ImageLinkShowDto;

/**
 * Created by Paul on 12/6/2015.
 */
public class ImageLinkMapper {

    public static ImageLinkShowDto map(ImageLink imageLink) {
        ImageLinkShowDto dto = new ImageLinkShowDto();
        dto.setId(imageLink.getId());
        if (imageLink.getLogo() != null) {
            dto.setLogo(imageLink.getLogo().getId());
        }
        dto.setName(imageLink.getName());
        dto.setDescription(imageLink.getDescription());
        dto.setUrl(imageLink.getUrl());
        return dto;
    }
}
