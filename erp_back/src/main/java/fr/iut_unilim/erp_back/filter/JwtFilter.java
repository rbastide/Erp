package fr.iut_unilim.erp_back.filter;

import fr.iut_unilim.erp_back.ErpBackApplication;
import fr.iut_unilim.erp_back.configuration.JwtUtils;
import fr.iut_unilim.erp_back.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;

    public JwtFilter(CustomUserDetailsService customUserDetailsService, JwtUtils jwtUtils) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        boolean pdfRequest = request.getRequestURI() != null && request.getRequestURI().startsWith("/api/pdf/");

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("auth_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        if (pdfRequest) {
            ErpBackApplication.LOGGER.info("PDF [auth] uri=" + request.getRequestURI() + ", jwtCookiePresent=" + (token != null));
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                String username = jwtUtils.extractUsername(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (jwtUtils.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    if (pdfRequest) {
                        ErpBackApplication.LOGGER.info("PDF [auth] authenticated user=" + username);
                    }
                } else if (pdfRequest) {
                    ErpBackApplication.LOGGER.warning("PDF [auth] token invalide");
                }
            } catch (RuntimeException e) {
                if (pdfRequest) {
                    ErpBackApplication.LOGGER.warning("PDF [auth] échec lecture token: " + e.getMessage());
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
