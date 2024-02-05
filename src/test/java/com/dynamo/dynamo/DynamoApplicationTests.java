package com.dynamo.dynamo;

import com.dynamo.dynamo.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class DynamoApplicationTests {

    @Autowired
    EmailService emailService;

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    void contextLoads() {

    }

    @Test
    void mailServiceTest() {
        String to = "shourya.sahu456@gmail.com";
        String subject = "Test Subject";
        String body = "Test Body";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        emailService.sendMail(subject, to, body);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

}
