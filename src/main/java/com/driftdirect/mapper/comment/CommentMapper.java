package com.driftdirect.mapper.comment;

import com.driftdirect.domain.comment.Comment;
import com.driftdirect.dto.comment.CommentDto;

/**
 * Created by Paul on 12/26/2015.
 */
public class CommentMapper {

    public static CommentDto map(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setComment(comment.getComment());
        dto.setPositive(comment.isPositive());
        return dto;
    }

}
