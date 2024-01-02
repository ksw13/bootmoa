package com.example.demo.dto;

import com.example.demo.domain.UserAccount;
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

    public static UserAccountDto from(UserAccount entity){
        return new UserAccountDto(
                entity.getUserId(),
                entity.getUserPassword()
        );
    }

    public UserAccount toEntity(){
        return new UserAccount(
                userId,
                userPassword
        );
    }
}
