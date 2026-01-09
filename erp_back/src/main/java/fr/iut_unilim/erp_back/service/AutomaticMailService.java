package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AutomaticMailService {

    private final JavaMailSender mailSender;
    private final ConnectionRepository connectionRepository;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.content}")
    private String content;

    @Value("${mail.subject}")
    private String subject;

    public AutomaticMailService(JavaMailSender mailSender, ConnectionRepository connectionRepository) {
        this.mailSender = mailSender;
        this.connectionRepository = connectionRepository;
    }

    //NE PAS SUPPRIMER LE SCHEDULER
    //@Scheduled(cron = "0 0 8 1 1-6,10-12 ?")
    //0sec | 0min | 8heures |tous les 1er du mois sauf Juillet, Août et Septembre | peu importe le jour de la semaine
    public void sendAutomaticMails() {

        List<String> emails = connectionRepository.findAllEmails();

        for (String email : emails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
        }
    }
}