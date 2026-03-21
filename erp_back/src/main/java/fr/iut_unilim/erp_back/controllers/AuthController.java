package fr.iut_unilim.erp_back.controllers;

import fr.iut_unilim.erp_back.configuration.JwtUtils;
import fr.iut_unilim.erp_back.dto.AuthResponse;
import fr.iut_unilim.erp_back.dto.EditUserRequest;
import fr.iut_unilim.erp_back.dto.LoginRequest;
import fr.iut_unilim.erp_back.dto.RegisterRequest;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Teacher;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import fr.iut_unilim.erp_back.repository.TeacherRepository;
import fr.iut_unilim.erp_back.service.ConnectionService;
import fr.iut_unilim.erp_back.service.RoleService;
import fr.iut_unilim.erp_back.service.TeacherService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ConnectionRepository connectionRepository;
    private final ConnectionService connectionService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;
    private final RoleService roleService;

    public AuthController(ConnectionRepository connectionRepository, ConnectionService connectionService, JwtUtils jwtUtils, AuthenticationManager authenticationManager, TeacherService teacherService, TeacherRepository teacherRepository, RoleService roleService) {
        this.connectionRepository = connectionRepository;
        this.connectionService = connectionService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
        this.roleService = roleService;
    }

    @PostMapping("/register")
    @PreAuthorize("@securityService.hasPermission('USER_MANAGEMENT')")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (connectionRepository.findByIdentifier(req.getIdentifier()) != null) {
            return ResponseEntity.badRequest().body("Username is already in use");
        }

        Connection user = new Connection();
        user.setIdentifier(req.getIdentifier());
        user.setEmail(req.getEmail());
        user.setRole(roleService.createOrAccessRoleByRoleName(req.getRole()));
        user.setUniversityDepartment(req.getUniversityDepartment());


        Connection connection = connectionRepository.save(user);
        teacherService.createTeacherFromRegister(req, connection);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/cas-login")
    public void casLogin() {
        // This endpoint is protected, so accessing it will trigger CAS authentication
        // The user will never reach this point - they'll be redirected to CAS
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
                resp.setRole(auth.getAuthorities().iterator().next().getAuthority());
                ResponseCookie cookie = ResponseCookie.from("auth_token", token)
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .sameSite("Lax")
                        .build();

                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .body(resp);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/user")
    @PreAuthorize("@securityService.hasPermission('USER_MANAGEMENT')")
    public ResponseEntity<?> editUser(@RequestBody EditUserRequest user) {
        Optional<Connection> existingUser = connectionRepository.findById(user.id());
        if (existingUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Connection userToEdit = existingUser.get();
        userToEdit.setIdentifier(user.identifier());
        userToEdit.setRole(roleService.createOrAccessRoleByRoleName(user.role()));
        userToEdit.setEmail(user.email());
        connectionRepository.save(userToEdit);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @PreAuthorize("@securityService.hasPermission('USER_MANAGEMENT')")
    public ResponseEntity<?> getUsers(Authentication authentication) {
        String userIdentifier = authentication.getName();

        return ResponseEntity.ok(connectionService.getAllConnectionFromDepartment(userIdentifier));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("@securityService.hasPermission('USER_MANAGEMENT')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Authentication authentication) {
        Optional<Connection> userToDelete = connectionRepository.findById(id);
        if (userToDelete.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String userToDeleteIdentifier = userToDelete.get().getIdentifier();
        if (userToDeleteIdentifier.equals(authentication.getName())) {
            return ResponseEntity.badRequest().body("Impossible de supprimer votre propre compte");
        }

        Teacher teacher = teacherService.getTeacherInfoByUser(id);
        if (teacher != null) {
            teacherRepository.delete(teacher);
        }
        connectionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user-info/{identifier}")
    @PreAuthorize("@securityService.isLogin()")
    public ResponseEntity<?> getUserInfo(@PathVariable String identifier) {
        Connection user = connectionRepository.findByIdentifier(identifier);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Teacher teacher = teacherService.getTeacherInfoByUser(user.getId());
        if (teacher == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Map.of("firstname", teacher.getFirstname(), "lastname", teacher.getLastname()));
    }

    @GetMapping("/user/department")
    @PreAuthorize("@securityService.isLogin()")
    public ResponseEntity<?> getUserDepartment(Authentication authentication) {
        Connection user = connectionRepository.findByIdentifier(authentication.getName());
        return ResponseEntity.ok(Map.of("departmentId", user.getUniversityDepartment().getUniversityDepartmentID()));
    }

    @PostMapping("/logout")
    @PreAuthorize("@securityService.isLogin()")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from("auth_token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Déconnexion réussie");
    }

    @PatchMapping("/users/department/{departmentId}")
    @PreAuthorize("@securityService.hasPermission('USER_MANAGEMENT')")
    public ResponseEntity<?> updateUserDepartment(@PathVariable Long departmentId, Authentication authentication) {
        String userIdentifier = authentication.getName();

        boolean wentGood = connectionService.updateDepartment(userIdentifier, departmentId);

        if (wentGood) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Département inconnu");
    }

    @GetMapping("/user-info-and-role/{identifier}")
    @PreAuthorize("@securityService.isLogin()")
    public ResponseEntity<?> getUserInfoAndRole(@PathVariable String identifier) {
        Connection user = connectionRepository.findByIdentifier(identifier);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Teacher teacher = teacherService.getTeacherInfoByUser(user.getId());
        if (teacher == null) {
            return ResponseEntity.notFound().build();
        }
        String roleName = user.getRole().getRoleName();

        return ResponseEntity.ok(Map.of(
                "role", roleName,
                "firstname", teacher.getFirstname(),
                "lastname", teacher.getLastname()
        ));
    }


}
