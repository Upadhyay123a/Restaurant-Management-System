package com.geekster.project.RestaurantManagementServiceAPI.Controller;

import com.geekster.project.RestaurantManagementServiceAPI.Model.User;
import com.geekster.project.RestaurantManagementServiceAPI.Repository.IUserRepo;
import com.geekster.project.RestaurantManagementServiceAPI.Service.EmailService;
import com.geekster.project.RestaurantManagementServiceAPI.security.JwtUtil;
import com.geekster.project.RestaurantManagementServiceAPI.security.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/*
 * Handles all authentication related operations for the restaurant system
 * Including user login, email verification, and password reset functionality
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    /* Authenticate user credentials and return JWT token for API access */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        User dbUser = userRepo.findByUserEmail(user.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update users who were created before the new security system
        if (dbUser.getHashedPassword() == null || dbUser.getHashedPassword().isEmpty()) {
            migrateExistingUser(dbUser);
        }

        // Check if user account is currently locked
        if (dbUser.isAccountLocked() && dbUser.getAccountLockedUntil() != null && 
            dbUser.getAccountLockedUntil().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Account is locked. Please try again later");
        }

        // Make sure new users verify their email before logging in
        if (dbUser.getVerificationToken() != null && !dbUser.isEmailVerified()) {
            throw new RuntimeException("Please verify your email before logging in");
        }

        // Validate user credentials
        if (!PasswordEncoderUtil.matches(user.getUserPassword(), dbUser.getHashedPassword())) {
            handleFailedLogin(dbUser);
            throw new RuntimeException("Invalid credentials");
        }

        // Reset failed attempts on successful login
        resetFailedLoginAttempts(dbUser);

        String token = jwtUtil.generateToken(dbUser.getUserEmail());
        return Map.of("token", token);
    }

    /* Help existing users transition to the new security system */
    private void migrateExistingUser(User user) {
        String hashedPassword = PasswordEncoderUtil.encodePassword(user.getUserPassword());
        user.setHashedPassword(hashedPassword);
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setTokenExpiry(null);
        user.setFailedLoginAttempts(0);
        user.setAccountLocked(false);
        user.setAccountLockedUntil(null);
        user.setLastFailedLogin(null);
        userRepo.save(user);
    }

    /* Track failed login attempts and lock account after too many tries */
    private void handleFailedLogin(User user) {
        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
        user.setLastFailedLogin(LocalDateTime.now());
        
        if (user.getFailedLoginAttempts() >= 5) {
            user.setAccountLocked(true);
            user.setAccountLockedUntil(LocalDateTime.now().plusHours(1));
        }
        
        userRepo.save(user);
    }

    /* Reset failed login count after user successfully logs in */
    private void resetFailedLoginAttempts(User user) {
        if (user.getFailedLoginAttempts() > 0) {
            user.setFailedLoginAttempts(0);
            user.setAccountLocked(false);
            user.setAccountLockedUntil(null);
            user.setLastFailedLogin(null);
            userRepo.save(user);
        }
    }

    /* Verify user email using the token sent during registration */
    @GetMapping("/verify")
    public Map<String, String> verifyEmail(@RequestParam String token) {
        User user = userRepo.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        if (user.getTokenExpiry() != null && user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification token has expired");
        }

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setTokenExpiry(null);
        userRepo.save(user);

        emailService.sendWelcomeEmail(user.getUserEmail(), user.getUserName());

        return Map.of(
            "message", "Email verified successfully! Your account has been activated.",
            "status", "VERIFIED",
            "userEmail", user.getUserEmail(),
            "nextStep", "Please login with your credentials."
        );
    }

    /* Send OTP to user's email for password reset */
    @PostMapping("/forgot-password")
    public Map<String, String> forgotPassword(@RequestBody Map<String, String> request) {
        String userEmail = request.get("userEmail");
        
        User user = userRepo.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = PasswordEncoderUtil.generateTwoFactorToken();
        user.setPasswordResetOtp(otp);
        user.setPasswordResetOtpExpiry(LocalDateTime.now().plusMinutes(10));
        user.setPasswordResetOtpVerified(false);
        user.setPasswordResetRequestedAt(LocalDateTime.now());
        
        userRepo.save(user);
        emailService.sendPasswordResetOtpEmail(user.getUserEmail(), user.getUserName(), otp);

        return Map.of(
            "message", "Password reset OTP sent to your email",
            "status", "OTP_SENT",
            "userEmail", userEmail,
            "otpExpiryMinutes", "10",
            "nextStep", "Enter OTP to reset password"
        );
    }

    /**
     * Verify OTP for password reset
     */
    @PostMapping("/verify-otp")
    public Map<String, String> verifyOtp(@RequestBody Map<String, String> request) {
        String userEmail = request.get("userEmail");
        String otp = request.get("otp");
        
        User user = userRepo.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPasswordResetOtpExpiry() != null && 
            user.getPasswordResetOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired");
        }

        if (!otp.equals(user.getPasswordResetOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        user.setPasswordResetOtpVerified(true);
        userRepo.save(user);

        return Map.of(
            "message", "OTP verified successfully",
            "status", "OTP_VERIFIED",
            "userEmail", userEmail,
            "nextStep", "Set new password"
        );
    }

    /**
     * Reset password with verified OTP
     */
    @PostMapping("/reset-password-with-otp")
    public Map<String, String> resetPasswordWithOtp(@RequestBody Map<String, String> request) {
        String userEmail = request.get("userEmail");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        User user = userRepo.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isPasswordResetOtpVerified()) {
            throw new RuntimeException("OTP not verified");
        }

        if (user.getPasswordResetOtpExpiry() != null && 
            user.getPasswordResetOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired");
        }

        if (!otp.equals(user.getPasswordResetOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (!PasswordEncoderUtil.validatePasswordStrength(newPassword)) {
            throw new RuntimeException("Password does not meet security requirements");
        }

        String hashedPassword = PasswordEncoderUtil.encodePassword(newPassword);
        user.setHashedPassword(hashedPassword);
        
        user.setPasswordResetOtp(null);
        user.setPasswordResetOtpExpiry(null);
        user.setPasswordResetOtpVerified(false);
        user.setPasswordResetRequestedAt(null);
        
        resetFailedLoginAttempts(user);
        
        userRepo.save(user);
        emailService.sendPasswordResetSuccessEmail(user.getUserEmail(), user.getUserName());

        return Map.of(
            "message", "Password reset successful",
            "status", "PASSWORD_RESET_SUCCESS",
            "userEmail", userEmail,
            "nextStep", "Login with new password"
        );
    }
}
