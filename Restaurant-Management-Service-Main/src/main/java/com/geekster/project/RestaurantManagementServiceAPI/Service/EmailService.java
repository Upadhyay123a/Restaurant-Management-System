/*
 * Handles all email communications for the restaurant system
 * Including test emails, verification emails, welcome emails, and password reset notifications
 */

package com.geekster.project.RestaurantManagementServiceAPI.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /* Send a test email to verify email configuration */
    public void sendTestEmail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("admin@restaurant.com");
            message.setSubject("Restaurant Management System - Email Test");
            message.setText("This is a test email from Restaurant Management System");
            message.setFrom("noreply@restaurant.com");

            mailSender.send(message);
            System.out.println("Test email sent successfully!");
        } catch (Exception e) {
            System.err.println("Failed to send test email: " + e.getMessage());
        }
    }

    /* Send verification email to newly registered users */
    public void sendVerificationEmail(String userEmail, String userName, String verificationToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(userEmail);
            message.setSubject("Verify Your Email - Restaurant Management System");
            message.setText("Dear " + userName + ",\n\nPlease click the link below to verify your email:\nhttp://localhost:8081/auth/verify?token=" + verificationToken + "\n\nIf you did not create this account, please ignore this email.\n\nBest regards,\nRestaurant Management Team");
            message.setFrom("noreply@restaurant.com");

            mailSender.send(message);
            System.out.println("Verification email sent to: " + userEmail);
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
        }
    }

    /* Send welcome email to users who have successfully verified their account */
    public void sendWelcomeEmail(String userEmail, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(userEmail);
            message.setSubject("Welcome to Restaurant Management System");
            message.setText("Dear " + userName + ",\n\nWelcome to Restaurant Management System! Your account has been created successfully.\n\nThank you for joining us!\n\nBest regards,\nRestaurant Management Team");
            message.setFrom("noreply@restaurant.com");

            mailSender.send(message);
            System.out.println("Welcome email sent to: " + userEmail);
        } catch (Exception e) {
            System.err.println("Failed to send welcome email: " + e.getMessage());
        }
    }

    /* Send password reset OTP email to users who request password reset */
    public void sendPasswordResetOtpEmail(String userEmail, String userName, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(userEmail);
            message.setSubject("Password Reset OTP - Restaurant Management System");
            message.setText("Dear " + userName + ",\n\nYour password reset OTP is: " + otp + "\n\nThis OTP will expire in 10 minutes.\n\nPlease use this OTP to reset your password.\n\nIf you did not request this password reset, please ignore this email.\n\nBest regards,\nRestaurant Management Team");
            message.setFrom("noreply@restaurant.com");

            mailSender.send(message);
            System.out.println("Password reset OTP email sent to: " + userEmail);
        } catch (Exception e) {
            System.err.println("Failed to send password reset OTP email: " + e.getMessage());
        }
    }

    /* Send confirmation email after successful password reset */
    public void sendPasswordResetSuccessEmail(String userEmail, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(userEmail);
            message.setSubject("Password Reset Successful - Restaurant Management System");
            message.setText("Dear " + userName + ",\n\nYour password has been successfully reset.\n\nYou can now login with your new password.\n\nIf you did not perform this action, please contact our support team.\n\nBest regards,\nRestaurant Management Team");
            message.setFrom("noreply@restaurant.com");

            mailSender.send(message);
            System.out.println("Password reset success email sent to: " + userEmail);
        } catch (Exception e) {
            System.err.println("Failed to send password reset success email: " + e.getMessage());
        }
    }
}
