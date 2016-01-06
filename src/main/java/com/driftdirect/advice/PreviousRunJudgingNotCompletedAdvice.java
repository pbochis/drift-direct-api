package com.driftdirect.advice;

import com.driftdirect.exception.PreviousRunJudgingNotCompletedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Paul on 1/6/2016.
 */

@ControllerAdvice
public class PreviousRunJudgingNotCompletedAdvice {
    @ExceptionHandler(value = PreviousRunJudgingNotCompletedException.class)
    public ResponseEntity<Object> handleException(final PreviousRunJudgingNotCompletedException e){
        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }
}
