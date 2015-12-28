package com.driftdirect.service;

import com.driftdirect.domain.comment.Comment;
import com.driftdirect.dto.comment.CommentCreateDto;
import com.driftdirect.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Paul on 12/26/2015.
 */
@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment findComment(Long id){
        return commentRepository.findOne(id);
    }

    public Comment findOrCreate(CommentCreateDto commentCreateDto) {
        if (commentCreateDto.getId() != null) {
            return commentRepository.findOne(commentCreateDto.getId());
        }
        Comment comment = new Comment();
        comment.setComment(commentCreateDto.getComment());
        comment.setPositive(commentCreateDto.isPositive());
        return commentRepository.save(comment);
    }

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }
}
