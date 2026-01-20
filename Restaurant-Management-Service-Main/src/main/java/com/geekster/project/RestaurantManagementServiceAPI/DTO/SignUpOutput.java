package com.geekster.project.RestaurantManagementServiceAPI.DTO;

public class SignUpOutput {

    private String status;
    private String message;

    public SignUpOutput(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
