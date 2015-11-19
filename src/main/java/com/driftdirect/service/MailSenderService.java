package com.driftdirect.service;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

/**
 * Created by Paul on 11/19/2015.
 */
@Component
public class MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(body, true);
        javaMailSender.send(message);
    }

    public void sendWithTemplate(String path, String[] args, String to, String subject) throws IOException, MessagingException {
        send(to, subject, buildMail(path, args));
    }
    // "/template/templateAccountCreated"
    public String buildMail(String path, String[] args) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        StringWriter writer = new StringWriter();
        IOUtils.copy(resource.getInputStream(), writer);
        String template = writer.toString();
        return MessageFormat.format(template, args);
    }
}
