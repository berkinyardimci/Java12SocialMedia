package com.socialmedia.service;

import com.socialmedia.rabbitmq.model.MailSenderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    public void sendMail(MailSenderModel mailSenderModel){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("berkinyardimci98@gmail.com");
        mailMessage.setTo(mailSenderModel.getEmail());
        mailMessage.setSubject("Hesap Oluşturma Başarılı......");
        mailMessage.setText("code: " + mailSenderModel.getActivationCode());
        mailMessage.setCc("berkinyardimci98@gmail.com");

        javaMailSender.send(mailMessage);
    }
}
