package com.geekster.project.RestaurantManagementServiceAPI.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Generate unique request ID
        String requestId = UUID.randomUUID().toString();
        request.setAttribute("requestId", requestId);
        
        // Create wrappers to cache request/response content
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        
        // Log request details
        logRequest(requestWrapper, requestId);
        
        long startTime = System.currentTimeMillis();
        
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            // Log response details
            long duration = System.currentTimeMillis() - startTime;
            logResponse(responseWrapper, requestId, duration);
        }
    }

    private void logRequest(ContentCachingRequestWrapper request, String requestId) {
        Map<String, Object> requestLog = new HashMap<>();
        requestLog.put("requestId", requestId);
        requestLog.put("method", request.getMethod());
        requestLog.put("uri", request.getRequestURI());
        requestLog.put("queryString", request.getQueryString());
        requestLog.put("remoteAddr", getClientIpAddress(request));
        requestLog.put("userAgent", request.getHeader("User-Agent"));
        requestLog.put("contentType", request.getContentType());
        requestLog.put("contentLength", request.getContentLength());
        requestLog.put("headers", getHeaders(request));
        
        // Log request body for POST/PUT requests (excluding sensitive data)
        if (shouldLogBody(request)) {
            String body = getContentAsString(request.getContentAsByteArray());
            requestLog.put("body", maskSensitiveData(body));
        }
        
        logger.info("📥 REQUEST: {}", requestLog);
    }

    private void logResponse(ContentCachingResponseWrapper response, String requestId, long duration) {
        Map<String, Object> responseLog = new HashMap<>();
        responseLog.put("requestId", requestId);
        responseLog.put("status", response.getStatus());
        responseLog.put("contentType", response.getContentType());
        responseLog.put("duration", duration + "ms");
        
        // Log response body for successful responses
        if (response.getStatus() >= 200 && response.getStatus() < 300) {
            String body = getContentAsString(response.getContentAsByteArray());
            responseLog.put("body", maskSensitiveData(body));
        }
        
        logger.info("📤 RESPONSE: {}", responseLog);
    }

    private boolean shouldLogBody(HttpServletRequest request) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        
        // Don't log sensitive endpoints
        if (uri.contains("/auth/") || uri.contains("/login")) {
            return false;
        }
        
        // Only log POST, PUT, PATCH requests
        return "POST".equals(method) || "PUT".equals(method) || "PATCH".equals(method);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0];
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            // Don't log sensitive headers
            if (!isSensitiveHeader(headerName)) {
                headers.put(headerName, request.getHeader(headerName));
            }
        }
        
        return headers;
    }

    private boolean isSensitiveHeader(String headerName) {
        String lowerName = headerName.toLowerCase();
        return lowerName.contains("authorization") || 
               lowerName.contains("password") || 
               lowerName.contains("token") || 
               lowerName.contains("secret") ||
               lowerName.contains("key");
    }

    private String getContentAsString(byte[] content) {
        if (content == null || content.length == 0) {
            return "";
        }
        
        try {
            return new String(content, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "[Error reading content: " + e.getMessage() + "]";
        }
    }

    private String maskSensitiveData(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }
        
        // Mask common sensitive fields
        String masked = content;
        masked = masked.replaceAll("(\"password\"\\s*:\\s*\")[^\"]*(\")", "$1*****$2");
        masked = masked.replaceAll("(\"token\"\\s*:\\s*\")[^\"]*(\")", "$1*****$2");
        masked = masked.replaceAll("(\"secret\"\\s*:\\s*\")[^\"]*(\")", "$1*****$2");
        masked = masked.replaceAll("(\"key\"\\s*:\\s*\")[^\"]*(\")", "$1*****$2");
        masked = masked.replaceAll("(\"hashedPassword\"\\s*:\\s*\")[^\"]*(\")", "$1*****$2");
        
        // Limit content length for logging
        if (masked.length() > 1000) {
            masked = masked.substring(0, 1000) + "... [truncated]";
        }
        
        return masked;
    }
}
