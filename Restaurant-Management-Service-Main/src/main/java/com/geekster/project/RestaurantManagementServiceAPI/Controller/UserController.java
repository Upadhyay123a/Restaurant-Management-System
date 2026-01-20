package com.geekster.project.RestaurantManagementServiceAPI.Controller;

import com.geekster.project.RestaurantManagementServiceAPI.Model.User;
import com.geekster.project.RestaurantManagementServiceAPI.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@Valid @RequestBody User user) {
        userService.signUpUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Integer userId,
                                             @Valid @RequestBody User user) {
        userService.updateUser(userId, user);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}
