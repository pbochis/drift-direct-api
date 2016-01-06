package com.driftdirect.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Paul on 1/6/2016.
 */
@ControllerAdvice
public class ControllerExceptionAdvice {
//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<Object> handleException(final Exception e){
//        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
