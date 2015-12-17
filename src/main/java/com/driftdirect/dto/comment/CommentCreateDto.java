package com.driftdirect.dto.comment;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by Paul on 12/16/2015.
 */
public class CommentCreateDto {
    @NotNull
    @Length(min = 3, max = 50)
    private String comment;

    @NotNull
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
}
