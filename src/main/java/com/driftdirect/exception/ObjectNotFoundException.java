package com.driftdirect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Paul on 11/11/2015.
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException(String msg){
        super(msg);
    }
}
