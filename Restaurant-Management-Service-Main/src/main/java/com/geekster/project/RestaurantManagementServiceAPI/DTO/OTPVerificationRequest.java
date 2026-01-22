package com.geekster.project.RestaurantManagementServiceAPI.DTO;

public class OTPVerificationRequest {
    private String userEmail;
    private String otp;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
