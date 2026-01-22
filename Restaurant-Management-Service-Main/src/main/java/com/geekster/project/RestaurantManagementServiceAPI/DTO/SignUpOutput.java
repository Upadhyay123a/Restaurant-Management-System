package com.geekster.project.RestaurantManagementServiceAPI.DTO;

public class SignUpOutput {
    private String message;
    private String status;

    public SignUpOutput() {
    }

    public SignUpOutput(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
