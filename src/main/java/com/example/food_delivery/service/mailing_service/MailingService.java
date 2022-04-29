package com.example.food_delivery.service.mailing_service;

import com.example.food_delivery.service.customer_order_management.CustomerOrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service responsible for sending emails.
 */
@Service
public class MailingService  {

    @Autowired
    private JavaMailSender emailSender;

    private static final Logger logger = LoggerFactory.getLogger(MailingService.class);

    /**
     * Sends an email to a given recipient, with the requested subject and content.
     * @param to is the email address of the recipient.
     * @param subject is the subject of the email to be sent.
     * @param text is the content of the email.
     */
    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("testb0020@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        logger.info(String.format("EVENT - sent email to %s about new order", to));
    }
}
