package com.driftdirect.dto.comment;

/**
 * Created by Paul on 12/16/2015.
 */
public class CommentCreateDto {
    private Long id;

    private String comment;

    private boolean positive;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

