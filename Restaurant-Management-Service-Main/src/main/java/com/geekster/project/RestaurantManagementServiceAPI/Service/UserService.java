package com.geekster.project.RestaurantManagementServiceAPI.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geekster.project.RestaurantManagementServiceAPI.DTO.SignUpOutput;
import com.geekster.project.RestaurantManagementServiceAPI.Model.User;
import com.geekster.project.RestaurantManagementServiceAPI.Repository.IUserRepo;
import com.geekster.project.RestaurantManagementServiceAPI.security.PasswordEncoderUtil;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private EmailService emailService;

    public SignUpOutput signUpUser(User user) {
        // Make sure password meets security requirements
        if (!PasswordEncoderUtil.validatePasswordStrength(user.getUserPassword())) {
            return new SignUpOutput("error", "Password must be at least 8 characters long and contain uppercase, lowercase, digit, and special character");
        }

        // Check if this email is already in use
        if (userRepo.findByUserEmail(user.getUserEmail()).isPresent()) {
            return new SignUpOutput("error", "Email already registered");
        }

        // Encrypt password for secure storage
        String hashedPassword = PasswordEncoderUtil.encodePassword(user.getUserPassword());
        user.setHashedPassword(hashedPassword);
        
        // Create email verification token valid for 24 hours
        String verificationToken = PasswordEncoderUtil.generateVerificationToken();
        user.setVerificationToken(verificationToken);
        user.setTokenExpiry(LocalDateTime.now().plusHours(24));
        user.setEmailVerified(false);
        
        // Set up default security settings for new account
        user.setFailedLoginAttempts(0);
        user.setAccountLocked(false);
        user.setAccountLockedUntil(null);
        user.setLastFailedLogin(null);
        
        // Set creation time
        user.setCreatedAt(LocalDateTime.now());
        
        // Store the user in database
        try {
            userRepo.save(user);
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
            e.printStackTrace();
            return new SignUpOutput("error", "Failed to register user: " + e.getMessage());
        }
        
        // Send verification email to new user
        try {
            emailService.sendVerificationEmail(user.getUserEmail(), user.getUserName(), verificationToken);
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
        }
        
        return new SignUpOutput("success", "User registered successfully. Please check your email for verification.");
    }

    public User updateUser(Integer userId, User updatedUser) {
        User existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Update user information with provided data
        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setUserEmail(updatedUser.getUserEmail());
        existingUser.setUserPassword(updatedUser.getUserPassword());
        // Update other fields as needed

        return userRepo.save(existingUser);
    }

    // Remove user from the system
    public void deleteUser(Integer userId) {
        User existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        userRepo.delete(existingUser);
    }

    // Find and return specific user
    public User getUserById(Integer userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    // Get all users from the database
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}



