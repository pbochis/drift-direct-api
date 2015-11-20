package com.driftdirect.service;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * Created by Paul on 11/19/2015.
 */
@Component
public class MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public Environment environment;

    public void send(String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(body, true);
        if (!Arrays.asList(environment.getActiveProfiles()).contains("dev")){
            javaMailSender.send(message);
        }
    }

    public void sendWithTemplate(String path, String[] args, String to, String subject) throws IOException, MessagingException {
        send(to, subject, buildMail(path, args));
    }

    public String buildMail(String path, String[] args) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        StringWriter writer = new StringWriter();
        IOUtils.copy(resource.getInputStream(), writer);
        String template = writer.toString();
        return MessageFormat.format(template, args);
    }
}
