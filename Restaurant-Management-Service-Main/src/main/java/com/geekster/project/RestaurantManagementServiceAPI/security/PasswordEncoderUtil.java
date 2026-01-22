package com.geekster.project.RestaurantManagementServiceAPI.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncoderUtil {

    private static final SecureRandom random = new SecureRandom();
    private static final int BCRYPT_ROUNDS = 12;

    public static String encodePassword(String rawPassword) {
        // Use BCrypt with proper rounds for professional security
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(BCRYPT_ROUNDS));
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        try {
            return BCrypt.checkpw(rawPassword, encodedPassword);
        } catch (Exception e) {
            return false;
        }
    }

    public static String generateVerificationToken() {
        // Generate cryptographically secure token
        byte[] token = new byte[32];
        random.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token);
    }

    public static String generateTwoFactorToken() {
        // Generate 6-digit 2FA token
        return String.format("%06d", random.nextInt(1000000));
    }

    public static boolean validatePasswordStrength(String password) {
        // Professional password policy validation
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}
