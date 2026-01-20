package com.geekster.project.RestaurantManagementServiceAPI.DTO;

import com.geekster.project.RestaurantManagementServiceAPI.Model.Visitor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SignUpInput {

    private Long userId;
    private String userName;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private Visitor visitor;

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Visitor getVisitor() {
        return visitor;
    }
}
