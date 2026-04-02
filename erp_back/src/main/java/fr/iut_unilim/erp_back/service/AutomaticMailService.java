package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import fr.iut_unilim.erp_back.repository.ResourceSheetRepository;
import fr.iut_unilim.erp_back.repository.TeacherResourceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class AutomaticMailService {
    private static final Set<String> TARGET_ROLE_NAMES = Set.of("Professeur", "Vacataire");

    private final JavaMailSender mailSender;
    private final ConnectionRepository connectionRepository;
    private final TeacherResourceRepository teacherResourceRepository;
    private final ResourceSheetRepository resourceSheetRepository;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.content}")
    private String content;

    @Value("${mail.subject}")
    private String subject;

    public AutomaticMailService(
            JavaMailSender mailSender,
            ConnectionRepository connectionRepository,
            TeacherResourceRepository teacherResourceRepository,
            ResourceSheetRepository resourceSheetRepository) {
        this.mailSender = mailSender;
        this.connectionRepository = connectionRepository;
        this.teacherResourceRepository = teacherResourceRepository;
        this.resourceSheetRepository = resourceSheetRepository;
    }

    //NE PAS SUPPRIMER LE SCHEDULER
    //@Scheduled(cron = "0 0 8 1 1-6,10-12 ?")
    //0sec | 0min | 8heures |tous les 1er du mois sauf Juillet, Août et Septembre | peu importe le jour de la semaine
    @Transactional(readOnly = true)
    public void sendAutomaticMails() {

        List<Connection> allUsers = connectionRepository.findAll();

        List<String> targetEmails = allUsers.stream()
                .filter(this::isTargetRoleConnection)
                .map(Connection::getEmail)
                .filter(this::isValidEmail)
                .distinct()
                .toList();

        for (String email : targetEmails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(content);

            try {
                mailSender.send(message);
            } catch (Exception e) {
                fr.iut_unilim.erp_back.ErpBackApplication.LOGGER.severe("Échec de l'envoi Postfix vers " + email + " : " + e.getMessage());
            }
        }
    }

    private List<String> extractEmails(Set<Long> userIds, Map<Long, Connection> connectionByUserId) {
        return userIds.stream()
                .map(connectionByUserId::get)
                .filter(Objects::nonNull)
                .map(Connection::getEmail)
                .filter(this::isValidEmail)
                .distinct()
                .toList();
    }

    private boolean isTargetRoleConnection(Connection connection) {
        if (connection == null || connection.getRole() == null || connection.getRole().getRoleName() == null) {
            return false;
        }
        String roleName = connection.getRole().getRoleName().trim();

        return TARGET_ROLE_NAMES.contains(roleName);
    }

    private boolean isValidEmail(String email) {
        return email != null && !email.isBlank();
    }

    private int getCurrentAcademicYearStart() {
        LocalDate now = LocalDate.now();
        return now.getMonthValue() >= 9 ? now.getYear() : now.getYear() - 1;
    }
}
