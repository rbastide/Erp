package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.ErpBackApplication;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import org.apache.poi.ddf.EscherRGBProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ConnectionRepository userRepository;

    public CustomUserDetailsService(ConnectionRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Connection user = userRepository.findByIdentifier(username);

        List<Connection> users = userRepository.findAll();

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getIdentifier(),
                user.getHashedPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRoleName())));
    }
}
