package com.geekster.project.RestaurantManagementServiceAPI.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    private int status;
    
    private String error;
    
    private String message;
    
    private String path;
    
    private String method;
    
    private String ipAddress;
    
    private Map<String, String> validationErrors;
    
    private String traceId;
    
    private String requestId;
    
    // Default constructor
    public ErrorResponse() {}
    
    // Builder pattern implementation
    public static Builder builder() {
        return new Builder();
    }
    
    // Getters and setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }
    
    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
    
    public String getTraceId() {
        return traceId;
    }
    
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
    
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    // Builder class
    public static class Builder {
        private ErrorResponse errorResponse = new ErrorResponse();
        
        public Builder timestamp(LocalDateTime timestamp) {
            errorResponse.setTimestamp(timestamp);
            return this;
        }
        
        public Builder status(int status) {
            errorResponse.setStatus(status);
            return this;
        }
        
        public Builder error(String error) {
            errorResponse.setError(error);
            return this;
        }
        
        public Builder message(String message) {
            errorResponse.setMessage(message);
            return this;
        }
        
        public Builder path(String path) {
            errorResponse.setPath(path);
            return this;
        }
        
        public Builder method(String method) {
            errorResponse.setMethod(method);
            return this;
        }
        
        public Builder ipAddress(String ipAddress) {
            errorResponse.setIpAddress(ipAddress);
            return this;
        }
        
        public Builder validationErrors(Map<String, String> validationErrors) {
            errorResponse.setValidationErrors(validationErrors);
            return this;
        }
        
        public Builder traceId(String traceId) {
            errorResponse.setTraceId(traceId);
            return this;
        }
        
        public Builder requestId(String requestId) {
            errorResponse.setRequestId(requestId);
            return this;
        }
        
        public ErrorResponse build() {
            return errorResponse;
        }
    }
}
