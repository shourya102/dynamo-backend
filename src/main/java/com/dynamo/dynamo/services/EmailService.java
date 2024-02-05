package com.dynamo.dynamo.services;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public String sendMail(String subject, String to, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
        return String.format("Email sent to %s with subject %s", to, subject);
    }

    public boolean validateEmail(String email) {
        try {
            InternetAddress internetAddress = new InternetAddress();
            internetAddress.setAddress(email);
            internetAddress.validate();
            return true;
        }
        catch (AddressException e) {
            return false;
        }
    }
}
