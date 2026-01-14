package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.configuration.JwtUtils;
import fr.iut_unilim.erp_back.dto.AuthResponse;
import fr.iut_unilim.erp_back.dto.EditUserRequest;
import fr.iut_unilim.erp_back.dto.LoginRequest;
import fr.iut_unilim.erp_back.dto.RegisterRequest;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Teacher;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import fr.iut_unilim.erp_back.service.ConnectionService;
import fr.iut_unilim.erp_back.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ConnectionRepository connectionRepository;
    private final ConnectionService connectionService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final TeacherService teacherService;


    public AuthController(ConnectionRepository connectionRepository, ConnectionService connectionService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AuthenticationManager authenticationManager, TeacherService teacherService) {
        this.connectionRepository = connectionRepository;
        this.connectionService = connectionService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.teacherService = teacherService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (connectionRepository.findByIdentifier(req.getIdentifier()) != null) {
            return ResponseEntity.badRequest().body("Username is already in use");
        }

        Connection user = new Connection();
        user.setIdentifier(req.getIdentifier());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(req.getRole());
        
        Connection connection = connectionRepository.save(user);

        if ("TEACHER".equalsIgnoreCase(req.getRole())) {
            teacherService.createTeacherFromRegister(req, connection);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getIdentifier(), req.getPassword())
            );
            if (auth.isAuthenticated()) {
                String subject = auth.getName();
                String token = jwtUtils.generateToken(subject);
                AuthResponse resp = new AuthResponse();
                resp.setToken(token);
                resp.setRole(auth.getAuthorities().iterator().next().getAuthority());
                return ResponseEntity.ok(resp);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> editUser(@RequestBody EditUserRequest user) {
        Optional<Connection> existingUser = connectionRepository.findById(user.id());
        if (existingUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Connection userToEdit = existingUser.get();
        userToEdit.setIdentifier(user.identifier());
        userToEdit.setRole(user.role());
        userToEdit.setEmail(user.email());
        if (user.newPassword() != null) {
            userToEdit.setPassword(passwordEncoder.encode(user.newPassword()));
        }
        connectionRepository.save(userToEdit);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(connectionService.getAllConnections());
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!connectionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        connectionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user-info")
    @PreAuthorize("hasAuthority('TEMP_TEACHER')")
    public ResponseEntity<?> getUserInfo(@RequestBody String identifier) {
        Connection user = connectionRepository.findByIdentifier(identifier);
        Teacher teacher = teacherService.getTeacherInfoByUser(user.getId());

        return ResponseEntity.ok(Map.of("firstname", teacher.getFirstname(), "lastname", teacher.getLastname()));
    }
}
