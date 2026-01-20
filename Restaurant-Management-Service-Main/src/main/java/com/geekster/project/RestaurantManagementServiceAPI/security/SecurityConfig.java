package com.geekster.project.RestaurantManagementServiceAPI.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

                // Signup endpoint
                .requestMatchers("/api/users/signup").permitAll()

                // Swagger / API docs
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // Static test HTML page
                .requestMatchers("/test-api.html").permitAll()

                // Everything else requires JWT authentication
                .anyRequest().authenticated()
            )

            // Add our JWT filter before the UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
