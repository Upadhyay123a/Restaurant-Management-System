package com.geekster.project.RestaurantManagementServiceAPI.security;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class RateLimitingService {

    private final Map<String, RateLimiterRegistry> rateLimiters = new ConcurrentHashMap<>();
    private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> lastRequestTime = new ConcurrentHashMap<>();

    public RateLimitingService() {
        // Initialize rate limiters for different endpoints
        initializeRateLimiters();
    }

    private void initializeRateLimiters() {
        // Login endpoint - stricter rate limiting
        RateLimiterConfig loginConfig = RateLimiterConfig.custom()
                .limitForPeriod(5)
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .timeoutDuration(Duration.ofSeconds(1))
                .build();
        
        RateLimiterRegistry loginRegistry = RateLimiterRegistry.of(loginConfig);
        rateLimiters.put("login", loginRegistry);

        // Signup endpoint - moderate rate limiting
        RateLimiterConfig signupConfig = RateLimiterConfig.custom()
                .limitForPeriod(3)
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .timeoutDuration(Duration.ofSeconds(1))
                .build();
        
        RateLimiterRegistry signupRegistry = RateLimiterRegistry.of(signupConfig);
        rateLimiters.put("signup", signupRegistry);

        // General API endpoints - lenient rate limiting
        RateLimiterConfig generalConfig = RateLimiterConfig.custom()
                .limitForPeriod(100)
                .limitRefreshPeriod(Duration.ofMinutes(1))
                .timeoutDuration(Duration.ofSeconds(1))
                .build();
        
        RateLimiterRegistry generalRegistry = RateLimiterRegistry.of(generalConfig);
        rateLimiters.put("general", generalRegistry);
    }

    @RateLimiter(name = "login", fallbackMethod = "rateLimitFallback")
    public boolean checkLoginRateLimit(String clientId) {
        return checkRateLimit("login", clientId);
    }

    @RateLimiter(name = "signup", fallbackMethod = "rateLimitFallback")
    public boolean checkSignupRateLimit(String clientId) {
        return checkRateLimit("signup", clientId);
    }

    @RateLimiter(name = "general", fallbackMethod = "rateLimitFallback")
    public boolean checkGeneralRateLimit(String clientId) {
        return checkRateLimit("general", clientId);
    }

    private boolean checkRateLimit(String endpoint, String clientId) {
        RateLimiterRegistry registry = rateLimiters.get(endpoint);
        if (registry != null) {
            io.github.resilience4j.ratelimiter.RateLimiter rateLimiter = registry.rateLimiter(endpoint + ":" + clientId);
            return rateLimiter.acquirePermission();
        }
        return true;
    }

    public boolean rateLimitFallback(String endpoint, String clientId, Exception e) {
        System.err.println("Rate limit exceeded for endpoint: " + endpoint + ", client: " + clientId);
        return false;
    }

    // Simple in-memory rate limiting as backup
    public boolean checkSimpleRateLimit(String clientId, int maxRequests, long timeWindowMs) {
        long currentTime = System.currentTimeMillis();
        Long lastTime = lastRequestTime.get(clientId);
        
        if (lastTime == null || (currentTime - lastTime) > timeWindowMs) {
            requestCounts.put(clientId, 1);
            lastRequestTime.put(clientId, currentTime);
            return true;
        }
        
        Integer count = requestCounts.getOrDefault(clientId, 0);
        if (count < maxRequests) {
            requestCounts.put(clientId, count + 1);
            return true;
        }
        
        return false;
    }

    public void resetRateLimit(String clientId) {
        requestCounts.remove(clientId);
        lastRequestTime.remove(clientId);
    }
}
