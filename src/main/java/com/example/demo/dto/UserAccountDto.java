package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserAccountDto {
    private String userId;
    private String userPassword;

    public UserAccountDto(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }
}
