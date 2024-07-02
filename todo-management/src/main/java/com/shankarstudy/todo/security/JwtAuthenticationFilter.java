package com.shankarstudy.todo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// This will be executed before Spring Security Filters
// Validate the JWT token and provides user details to spring security for Authentication

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider theJwtTokenProvider, UserDetailsService theUserDetailsService) {
        this.jwtTokenProvider = theJwtTokenProvider;
        this.userDetailsService = theUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Get JWT Token from HTTP Request
        String token = getTokenFromRequest(request);

        // Validate token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // Get Username from token
            String username = jwtTokenProvider.getUsername(token);

            // Load user associated with given token from DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Create instance of UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // Pass all the user details to UsernamePasswordAuthenticationToken obj
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Pass UsernamePasswordAuthenticationToken obj to Security Context holder
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        // Get bearer token from 'Authorization' key
        String bearerToken = request.getHeader("Authorization");

        // Extract token from Bearer token
        if (StringUtils.hasText((bearerToken)) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
