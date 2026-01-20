package com.geekster.project.RestaurantManagementServiceAPI.DTO;

public class SignInOutput {

    private String status;
    private String token;

    public SignInOutput(String status, String token) {
        this.status = status;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }
}
