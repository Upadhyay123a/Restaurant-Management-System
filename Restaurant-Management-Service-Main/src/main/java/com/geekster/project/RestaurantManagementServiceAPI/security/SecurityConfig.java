package com.geekster.project.RestaurantManagementServiceAPI.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF (since we are using JWT)
            .csrf(csrf -> csrf.disable())

            // Define which endpoints are accessible without JWT
            .authorizeHttpRequests(auth -> auth
                // Authentication endpoints
                .requestMatchers("/auth/**").permitAll()

                // Email verification endpoint
                .requestMatchers("/api/auth/verify").permitAll()

                // Signup endpoint
                .requestMatchers("/api/users/**").permitAll()

                // Visitor signup endpoint
                .requestMatchers("/api/visitors/**").permitAll()

                // Food endpoints for testing
                .requestMatchers("/api/fooditems/**").permitAll()

                // Health check endpoints
                .requestMatchers("/api/health/**").permitAll()

                // Static HTML files
                .requestMatchers("/test-api.html").permitAll()
                .requestMatchers("/restaurant-dashboard.html").permitAll()

                // Analytics endpoints
                .requestMatchers("/api/analytics/**").permitAll()

                // Email test endpoint
                .requestMatchers("/email/**").permitAll()

                // Swagger / API docs
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // Static test HTML page
                .requestMatchers("/test-api.html").permitAll()

                // Actuator health check
                .requestMatchers("/actuator/health").permitAll()

                // Everything else requires JWT authentication
                .anyRequest().authenticated()
            )

            // Add our JWT filter before the UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

            // Configure session management
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Configure security headers
            .headers(headers -> headers
                .frameOptions().deny()
                .contentTypeOptions()
            );

        return http.build();
    }
}
