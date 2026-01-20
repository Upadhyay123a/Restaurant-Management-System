package com.geekster.project.RestaurantManagementServiceAPI.Controller;

import com.geekster.project.RestaurantManagementServiceAPI.Model.User;
import com.geekster.project.RestaurantManagementServiceAPI.Repository.IUserRepo;
import com.geekster.project.RestaurantManagementServiceAPI.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {

        User dbUser = userRepo.findByUserEmail(user.getUserEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!dbUser.getUserPassword().equals(user.getUserPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(dbUser.getUserEmail());
        return Map.of("token", token);
    }
}
