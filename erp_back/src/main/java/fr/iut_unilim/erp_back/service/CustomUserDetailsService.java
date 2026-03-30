package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static fr.iut_unilim.erp_back.tools.utils.HashGenerator.generateBlindIndex;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ConnectionRepository userRepository;

    public CustomUserDetailsService(ConnectionRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String hashedIdentifier;
        try {
            hashedIdentifier = generateBlindIndex(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        Optional<Connection> userOptional = userRepository.findByHashedIdentifier(hashedIdentifier);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                userOptional.get().getIdentifier(),
                "",
                Collections.singletonList(new SimpleGrantedAuthority(userOptional.get().getRole().getRoleName())));
    }
}
