package com.driftdirect.advice;

import com.driftdirect.exception.QualifierAlreadyJudgeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Paul on 1/27/2016.
 */
@ControllerAdvice
public class QualifierAlreadyJudgeAdvice {
    @ExceptionHandler(value = QualifierAlreadyJudgeException.class)
    public ResponseEntity<Object> handleException(final QualifierAlreadyJudgeException e) {
        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }
}