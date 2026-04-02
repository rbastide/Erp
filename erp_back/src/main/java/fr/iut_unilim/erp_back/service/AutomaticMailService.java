package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.AutomaticMailConfig;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.repository.AutomaticMailRepository;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import fr.iut_unilim.erp_back.repository.ResourceSheetRepository;
import fr.iut_unilim.erp_back.repository.TeacherResourceRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AutomaticMailService {
    private static final Set<String> TARGET_ROLE_NAMES = Set.of("Professeur", "Vacataire");

    private final JavaMailSender mailSender;
    private final ConnectionRepository connectionRepository;
    private final AutomaticMailRepository configRepository;
    private ThreadPoolTaskScheduler taskScheduler;
    private final Map<Long, java.util.concurrent.ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

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
            ResourceSheetRepository resourceSheetRepository, AutomaticMailRepository configRepository) {
        this.mailSender = mailSender;
        this.connectionRepository = connectionRepository;
        this.configRepository = configRepository;
    }

    @PostConstruct
    public void initScheduler() {
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        taskScheduler.setThreadNamePrefix("AutoMail-");
        taskScheduler.initialize();
        reloadSchedules();
        System.out.println("Heure actuelle du serveur : " + java.time.LocalDateTime.now());
    }

    public void reloadSchedules() {
        scheduledTasks.values().forEach(task -> task.cancel(false));
        scheduledTasks.clear();

        List<AutomaticMailConfig> configs = configRepository.findAll();
        for (AutomaticMailConfig config : configs) {
            String cron = generateCron(config);
            if (cron != null) {
                java.util.concurrent.ScheduledFuture<?> future = taskScheduler.schedule(
                        this::sendAutomaticMails,
                        new CronTrigger(cron, java.util.TimeZone.getTimeZone("Europe/Paris"))
                );
                scheduledTasks.put(config.getId(), future);
            }
        }
    }

    private String generateCron(AutomaticMailConfig config) {
        try {
            String[] timeParts = config.getTime().split(":");
            String hour = timeParts[0];
            String minute = timeParts[1];
            String day = String.valueOf(config.getDayOfMonth());
            String month = config.getMonths() == null || config.getMonths().isEmpty() ? "*" : config.getMonths();
            return "0 " + minute + " " + hour + " " + day + " " + month + " ?";
        } catch (Exception e) {
            return null;
        }
    }

    public List<AutomaticMailConfig> getAllConfigs() {
        return configRepository.findAll();
    }

    public AutomaticMailConfig saveConfig(AutomaticMailConfig config) {
        AutomaticMailConfig saved = configRepository.save(config);
        reloadSchedules();
        return saved;
    }

    public void deleteConfig(Long id) {
        configRepository.deleteById(id);
        reloadSchedules();
    }

    @Transactional(readOnly = true)
    public void sendAutomaticMails() {
        //Pour tester dans les logs : fr.iut_unilim.erp_back.ErpBackApplication.LOGGER.info(">>> Déclenchement de la tâche planifiée d'envoi de mails...");
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
        //Pour tester dans les logs : fr.iut_unilim.erp_back.ErpBackApplication.LOGGER.info(">>> Fin de la tâche planifiée. Mails cibles : " + targetEmails.size());
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
