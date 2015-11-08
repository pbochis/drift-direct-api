package com.driftdirect.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * Created by Paul on 11/6/2015.
 */

@RestController
public class HelloWorldController {
    @RequestMapping(path = "/greeting", method = RequestMethod.GET)
    public String greeting(@RequestParam(value = "name", defaultValue = "World")String name){
        return "Buna, Iancu!";
    }
}
