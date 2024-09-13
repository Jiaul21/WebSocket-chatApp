package com.jiaul.websocket_chatapp_server.config;


import com.jiaul.websocket_chatapp_server.service.JwtService;
import com.jiaul.websocket_chatapp_server.service.UserCredentialService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserCredentialService userCredentialService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        System.out.println("///////// doFilterInternal /////////");
        final String authHeader = request.getHeader("Authorization");
        final String tokenValue;
        final String userName;

        System.out.println("header:----- "+authHeader);

        if (authHeader == null || authHeader.isBlank()) {
            System.out.println("///////// doFilterInternal 2 /////////");
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("///////// doFilterInternal 3 /////////");
        tokenValue = authHeader.substring(7);
        userName = jwtService.extractUsername(tokenValue);


        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userCredentialService.loadUserByUsername(userName);
            if (jwtService.isTokenValid(tokenValue, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}


