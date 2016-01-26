package com.driftdirect.config;

import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Created by Paul on 12/27/2015.
 */
@Configuration
public class RestConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("10mb");
        factory.setMaxRequestSize("10mb");
        return factory.createMultipartConfig();
    }
}
