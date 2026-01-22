package com.geekster.project.RestaurantManagementServiceAPI.Service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OTPService {

    private Map<String, OTPData> otpStorage = new HashMap<>();

    public String generateOTP(String userEmail) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        OTPData otpData = new OTPData(otp, System.currentTimeMillis() + 600000); // 10 minutes expiry
        otpStorage.put(userEmail, otpData);
        return otp;
    }

    public boolean validateOTP(String userEmail, String otp) {
        OTPData otpData = otpStorage.get(userEmail);
        if (otpData == null) {
            return false;
        }
        
        if (System.currentTimeMillis() > otpData.expiryTime) {
            otpStorage.remove(userEmail);
            return false;
        }
        
        return otpData.otp.equals(otp);
    }

    private static class OTPData {
        String otp;
        long expiryTime;

        OTPData(String otp, long expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }
}
