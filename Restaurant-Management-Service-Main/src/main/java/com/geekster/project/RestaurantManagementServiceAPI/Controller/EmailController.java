package com.geekster.project.RestaurantManagementServiceAPI.Controller;

import com.geekster.project.RestaurantManagementServiceAPI.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/test")
    public String testEmail() {
        try {
            emailService.sendTestEmail();
            return "Test email sent successfully!";
        } catch (Exception e) {
            return "Failed to send test email: " + e.getMessage();
        }
    }
}
