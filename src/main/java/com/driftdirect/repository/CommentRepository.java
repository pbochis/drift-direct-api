package com.driftdirect.repository;

import com.driftdirect.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 12/16/2015.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
