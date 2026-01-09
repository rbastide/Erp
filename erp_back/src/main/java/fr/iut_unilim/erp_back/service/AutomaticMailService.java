package fr.iut_unilim.erp_back.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AutomaticMailService {

    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;

    public AutomaticMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @PostConstruct
    public void debug() {
        System.out.println("Mail Expéditeur = " + from);
    }

    public void sendAutomaticMail(String destination, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(destination);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
