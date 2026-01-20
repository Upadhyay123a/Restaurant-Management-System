package com.geekster.project.RestaurantManagementServiceAPI.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    private String tokenValue;
    private LocalDateTime tokenCreationDateTime;

    @OneToOne
    @JoinColumn(name = "fk_user_Id")
    private User user;

    public AuthenticationToken() {}

    public AuthenticationToken(User user) {
        this.user = user;
        this.tokenValue = UUID.randomUUID().toString();
        this.tokenCreationDateTime = LocalDateTime.now();
    }

    public Long getTokenId() {
        return tokenId;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public LocalDateTime getTokenCreationDateTime() {
        return tokenCreationDateTime;
    }

    public User getUser() {
        return user;
    }
}
