package com.geekster.project.RestaurantManagementServiceAPI.security;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class SecurityAuditService {

    private final Map<String, Integer> failedAttempts = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> lastFailedAttempt = new ConcurrentHashMap<>();

    public void logSecurityEvent(String eventType, String userEmail, String ipAddress, String details) {
        String logEntry = String.format("[%s] %s - User: %s, IP: %s, Details: %s", 
            LocalDateTime.now(), eventType, userEmail, ipAddress, details);
        
        System.out.println("🔒 SECURITY AUDIT: " + logEntry);
        
        // Log to file in production
        // logToFile(logEntry);
    }

    public void recordFailedAttempt(String userEmail, String ipAddress) {
        failedAttempts.merge(userEmail, 1, Integer::sum);
        lastFailedAttempt.put(userEmail, LocalDateTime.now());
        
        logSecurityEvent("FAILED_LOGIN_ATTEMPT", userEmail, ipAddress, 
            "Total attempts: " + failedAttempts.get(userEmail));
    }

    public void recordSuccessfulLogin(String userEmail, String ipAddress) {
        failedAttempts.remove(userEmail);
        lastFailedAttempt.remove(userEmail);
        
        logSecurityEvent("SUCCESSFUL_LOGIN", userEmail, ipAddress, "Login successful");
    }

    public void recordAccountLocked(String userEmail, String ipAddress) {
        logSecurityEvent("ACCOUNT_LOCKED", userEmail, ipAddress, 
            "Account locked due to multiple failed attempts");
    }

    public void recordPasswordChange(String userEmail, String ipAddress) {
        logSecurityEvent("PASSWORD_CHANGED", userEmail, ipAddress, "Password changed successfully");
    }

    public void recordEmailVerified(String userEmail, String ipAddress) {
        logSecurityEvent("EMAIL_VERIFIED", userEmail, ipAddress, "Email verification completed");
    }

    public void recordSuspiciousActivity(String userEmail, String ipAddress, String details) {
        logSecurityEvent("SUSPICIOUS_ACTIVITY", userEmail, ipAddress, details);
    }

    public int getFailedAttempts(String userEmail) {
        return failedAttempts.getOrDefault(userEmail, 0);
    }

    public LocalDateTime getLastFailedAttempt(String userEmail) {
        return lastFailedAttempt.get(userEmail);
    }

    public void resetFailedAttempts(String userEmail) {
        failedAttempts.remove(userEmail);
        lastFailedAttempt.remove(userEmail);
    }
}
