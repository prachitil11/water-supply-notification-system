package com.watersupply.Notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService
{
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to,String message)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Water Supply Notification");
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}