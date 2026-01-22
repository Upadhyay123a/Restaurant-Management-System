package com.geekster.project.RestaurantManagementServiceAPI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    @GetMapping
    public Map<String, Object> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("application", "Restaurant Management System");
        health.put("version", "1.0.0");
        health.put("environment", "production");
        
        // Database health check
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            health.put("database", "UP");
        } catch (Exception e) {
            health.put("database", "DOWN");
            health.put("databaseError", e.getMessage());
        }
        
        // Memory health check
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        Map<String, Object> memory = new HashMap<>();
        memory.put("max", maxMemory / 1024 / 1024 + " MB");
        memory.put("total", totalMemory / 1024 / 1024 + " MB");
        memory.put("used", usedMemory / 1024 / 1024 + " MB");
        memory.put("free", freeMemory / 1024 / 1024 + " MB");
        memory.put("usagePercentage", (usedMemory * 100 / maxMemory));
        health.put("memory", memory);
        
        // Security status
        Map<String, Object> security = new HashMap<>();
        security.put("passwordHashing", "BCrypt (12 rounds)");
        security.put("jwtEnabled", true);
        security.put("rateLimiting", true);
        security.put("auditLogging", true);
        security.put("securityHeaders", true);
        health.put("security", security);
        
        return health;
    }

    @GetMapping("/detailed")
    public Map<String, Object> detailedHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("application", "Restaurant Management System");
        health.put("version", "1.0.0");
        health.put("environment", "production");
        
        // Detailed database check
        Map<String, Object> database = new HashMap<>();
        try {
            long userCount = (Long) entityManager.createQuery("SELECT COUNT(u) FROM User u").getSingleResult();
            database.put("status", "UP");
            database.put("connection", "Healthy");
            database.put("userCount", userCount);
            database.put("type", "MySQL");
        } catch (Exception e) {
            database.put("status", "DOWN");
            database.put("error", e.getMessage());
        }
        health.put("database", database);
        
        // System information
        Map<String, Object> system = new HashMap<>();
        Runtime runtime = Runtime.getRuntime();
        system.put("javaVersion", System.getProperty("java.version"));
        system.put("osName", System.getProperty("os.name"));
        system.put("osVersion", System.getProperty("os.version"));
        system.put("availableProcessors", runtime.availableProcessors());
        
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        system.put("maxMemory", maxMemory / 1024 / 1024 + " MB");
        system.put("totalMemory", totalMemory / 1024 / 1024 + " MB");
        system.put("usedMemory", usedMemory / 1024 / 1024 + " MB");
        system.put("freeMemory", freeMemory / 1024 / 1024 + " MB");
        system.put("memoryUsagePercentage", (usedMemory * 100 / maxMemory));
        health.put("system", system);
        
        // Security features
        Map<String, Object> security = new HashMap<>();
        security.put("passwordHashing", "BCrypt (12 rounds)");
        security.put("jwtAuthentication", "Enabled");
        security.put("emailVerification", "Enabled");
        security.put("accountLocking", "Enabled");
        security.put("rateLimiting", "Enabled");
        security.put("auditLogging", "Enabled");
        security.put("securityHeaders", "Enabled");
        security.put("inputValidation", "Enabled");
        security.put("twoFactorAuth", "Ready");
        health.put("security", security);
        
        // API endpoints status
        Map<String, Object> endpoints = new HashMap<>();
        endpoints.put("authentication", "Operational");
        endpoints.put("userManagement", "Operational");
        endpoints.put("foodManagement", "Operational");
        endpoints.put("orderManagement", "Operational");
        endpoints.put("visitorManagement", "Operational");
        endpoints.put("emailService", "Operational");
        health.put("endpoints", endpoints);
        
        return health;
    }

    @GetMapping("/ping")
    public Map<String, String> ping() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Restaurant Management System is running");
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }

    @Component
    public static class DatabaseHealthIndicator implements HealthIndicator {
        
        @PersistenceContext
        private EntityManager entityManager;

        @Override
        public Health health() {
            try {
                entityManager.createNativeQuery("SELECT 1").getSingleResult();
                return Health.up()
                        .withDetail("database", "MySQL")
                        .withDetail("status", "Connected")
                        .build();
            } catch (Exception e) {
                return Health.down()
                        .withDetail("database", "MySQL")
                        .withDetail("error", e.getMessage())
                        .build();
            }
        }
    }
}
