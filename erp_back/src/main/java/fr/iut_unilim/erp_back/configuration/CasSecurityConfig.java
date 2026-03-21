package fr.iut_unilim.erp_back.configuration;


import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.filter.JwtFilter;
import fr.iut_unilim.erp_back.repository.ConnectionRepository;
import fr.iut_unilim.erp_back.service.CustomUserDetailsService;
import org.apereo.cas.client.validation.Cas30ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Profile("prod")
public class CasSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils JwtUtils;
    private final ConnectionRepository connectionRepository;

    @Value("${cas.server}")
    private String casServerUrl;

    @Value("${backend.url}")
    private String backendUrl;

    @Value("${frontend.url}")
    private String frontendUrl;

    public CasSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtUtils jwtUtils, ConnectionRepository connectionRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.JwtUtils = jwtUtils;
        this.connectionRepository = connectionRepository;
    }


    // Redirection back vers CAS
    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(backendUrl + "/login/cas");
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }


    // Redirection CAS vers page login CAS
    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        entryPoint.setLoginUrl(casServerUrl + "/login");
        entryPoint.setServiceProperties(serviceProperties());
        return entryPoint;
    }


    // Valider/Vérifier et récupérer les données
    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider provider = new CasAuthenticationProvider(); //Instancie le composant Spring security permettant de traiter la connexion CAS
        provider.setServiceProperties(serviceProperties()); // Redirige vers l'url CAS
        provider.setTicketValidator(new Cas30ServiceTicketValidator(casServerUrl));// Vérifier le ticket
        provider.setUserDetailsService(customUserDetailsService); // VA chercher les droits/role de l'user
        provider.setKey("casProviderKey"); // Ajoute une clé de sécurité interne. (pas obligatoire askip)
        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler casSuccessHandler() {
        return (request, response, authentication) -> {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            String email = "";

            if (authentication instanceof CasAuthenticationToken casToken) {
                Map<String,Object> attributes = casToken.getAssertion().getPrincipal().getAttributes();

                Connection user = connectionRepository.findByIdentifier(username);


                boolean needUpdate = false;
                if (user != null) {

                    if (attributes.containsKey("mail")) {
                        String casEmail = (String) attributes.get("mail");

                        if (casEmail != null && !casEmail.equals(user.getEmail())) {
                            user.setEmail(casEmail);
                            needUpdate = true;
                        }
                    }

                    if (attributes.containsKey("sn")) {
                        user.setLastName((String) attributes.get("sn"));
                        needUpdate = true;
                    }

                    if (attributes.containsKey("givenName")) {
                        user.setFirstName((String) attributes.get("givenName"));
                        needUpdate = true;
                    }
                }

                    if (needUpdate) {
                        connectionRepository.save(user);
                    }
                }

            String jwt = JwtUtils.generateToken(username);
            response.sendRedirect(frontendUrl + "/success?token=" + jwt);
        };
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter(AuthenticationManager authenticationManager) {
        CasAuthenticationFilter filter = new CasAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationManager(new ProviderManager(Collections.singletonList(casAuthenticationProvider())));
        filter.setServiceProperties(serviceProperties());
        filter.setFilterProcessesUrl("/login/cas");
        filter.setAuthenticationSuccessHandler(casSuccessHandler());

        return filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/login/cas").permitAll()
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(casAuthenticationEntryPoint())
                )
                .addFilterBefore(casAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(customUserDetailsService, JwtUtils), CasAuthenticationFilter.class)
                .build();
    }

    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        var config = new org.springframework.web.cors.CorsConfiguration();
        config.setAllowedOrigins(java.util.List.of(frontendUrl));
        config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(java.util.List.of("Content-Type", "Authorization"));
        config.setAllowCredentials(true);

        var source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(casAuthenticationProvider()));
    }
}
