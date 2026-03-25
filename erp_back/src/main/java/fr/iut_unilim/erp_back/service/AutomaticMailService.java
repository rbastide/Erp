package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Mccc;
import fr.iut_unilim.erp_back.entity.TeacherResource;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import fr.iut_unilim.erp_back.repository.McccRepository;
import fr.iut_unilim.erp_back.repository.UniversityDepartmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AutomaticMailService {
    private static final String ROLE_PROFESSEUR = "Professeur";
    private static final String ROLE_VACATAIRE = "Vacataire";
    private static final Set<String> TARGET_ROLE_NAMES = Set.of(
            ROLE_PROFESSEUR.toUpperCase(),
            ROLE_VACATAIRE.toUpperCase()
    );

    private final JavaMailSender mailSender;
    private final ConnectionRepository connectionRepository;
    private final McccRepository mcccRepository;
    private final ConnectionService connectionService;
    private final UniversityDepartmentRepository universityDepartmentRepository;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.content}")
    private String content;

    @Value("${mail.subject}")
    private String subject;

    public AutomaticMailService(JavaMailSender mailSender, ConnectionRepository connectionRepository, McccRepository mcccRepository, ConnectionService connectionService, UniversityDepartmentRepository universityDepartmentRepository) {
        this.mailSender = mailSender;
        this.connectionRepository = connectionRepository;
        this.mcccRepository = mcccRepository;
        this.connectionService = connectionService;
        this.universityDepartmentRepository = universityDepartmentRepository;
    }

    //NE PAS SUPPRIMER LE SCHEDULER
    //@Scheduled(cron = "0 0 8 1 1-6,10-12 ?")
    //0sec | 0min | 8heures |tous les 1er du mois sauf Juillet, Août et Septembre | peu importe le jour de la semaine
    @Transactional(readOnly = true)
    public AutomaticMailReport sendAutomaticMails() {
        AutomaticMailAudience audience = getAutomaticMailAudience();
        int sentCount = 0;
        int failedCount = 0;
        List<String> failedEmails = new ArrayList<>();

        for (String email : audience.concernedEmails()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(content);

            try {
                mailSender.send(message);
                sentCount++;
            } catch (Exception e) {
                failedCount++;
                failedEmails.add(email);
            }
        }

        return new AutomaticMailReport(
                audience.academicYearStart(),
                sentCount,
                failedCount,
                audience.concernedEmails().size(),
                audience.nonConcernedEmails().size(),
                failedEmails
        );
    }

    @Transactional(readOnly = true)
    public AutomaticMailAudience getAutomaticMailAudience() {
        int academicYearStart = getCurrentAcademicYearStart();
        List<Connection> teachersAndVacataires = getTeacherAndVacataireConnections();
        Map<Long, Connection> connectionByUserId = teachersAndVacataires.stream()
                .collect(Collectors.toMap(Connection::getId, connection -> connection, (existing, replacement) -> existing));

        Set<Long> teacherUserIds = connectionByUserId.keySet().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (teacherUserIds.isEmpty()) {
            return new AutomaticMailAudience(academicYearStart, List.of(), List.of(), List.of(), List.of());
        }

        Set<Long> fillerUserIds = new LinkedHashSet<>();
        Set<Long> validatorUserIds = new LinkedHashSet<>();
        List<Mccc> currentYearMcccs = mcccRepository.findAll().stream()
                .filter(mccc -> Objects.equals(mccc.getAcademicYearStart(), academicYearStart))
                .toList();

        for (Mccc mccc : currentYearMcccs) {
            Set<TeacherResource> teacherResources = mccc.getTeacherResources();
            if (teacherResources == null) continue;

            for (TeacherResource teacherResource : teacherResources) {
                Connection teacherConnection = teacherResource.getConnection();
                if (teacherConnection == null || !teacherUserIds.contains(teacherConnection.getId())) continue;

                if (teacherResource.getIsManager()) {
                    validatorUserIds.add(teacherConnection.getId());
                } else {
                    fillerUserIds.add(teacherConnection.getId());
                }
            }
        }

        Set<Long> concernedUserIds = new LinkedHashSet<>(fillerUserIds);
        concernedUserIds.addAll(validatorUserIds);

        List<String> concernedEmails = extractEmails(concernedUserIds, connectionByUserId);
        List<String> nonConcernedEmails = extractEmails(
                teacherUserIds.stream()
                        .filter(userId -> !concernedUserIds.contains(userId))
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                connectionByUserId
        );

        return new AutomaticMailAudience(
                academicYearStart,
                concernedEmails,
                nonConcernedEmails,
                extractEmails(fillerUserIds, connectionByUserId),
                extractEmails(validatorUserIds, connectionByUserId)
        );
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

    private boolean isValidEmail(String email) {
        return email != null && !email.isBlank();
    }

    private boolean isTargetRoleConnection(Connection connection) {
        if (connection == null || connection.getRole() == null || connection.getRole().getRoleName() == null) {
            return false;
        }

        String roleName = connection.getRole().getRoleName().trim().toUpperCase();
        return TARGET_ROLE_NAMES.contains(roleName);
    }

    private List<Connection> getTeacherAndVacataireConnections() {
        LinkedHashSet<Connection> connections = new LinkedHashSet<>();
        String[] roleNames = {ROLE_PROFESSEUR, ROLE_VACATAIRE};

        universityDepartmentRepository.findAll().forEach(department -> {
            try {
                connections.addAll(connectionService.getAllConnectionsFromDepartmentAndRoleNames(department, roleNames));
            } catch (RuntimeException ignored) {
                // Fallback local strict sur role.roleName si la récupération par noms échoue.
                List<Connection> departmentConnections = connectionRepository.findAllByUniversityDepartment(department);
                connections.addAll(departmentConnections.stream().filter(this::isTargetRoleConnection).toList());
            }
        });

        return new ArrayList<>(connections);
    }

    private int getCurrentAcademicYearStart() {
        LocalDate now = LocalDate.now();
        return now.getMonthValue() >= 9 ? now.getYear() : now.getYear() - 1;
    }

    public record AutomaticMailAudience(
            int academicYearStart,
            List<String> concernedEmails,
            List<String> nonConcernedEmails,
            List<String> fillerTeacherEmails,
            List<String> validatorTeacherEmails
    ) {
    }

    public record AutomaticMailReport(
            int academicYearStart,
            int sentCount,
            int failedCount,
            int concernedCount,
            int nonConcernedCount,
            List<String> failedEmails
    ) {
    }
}
