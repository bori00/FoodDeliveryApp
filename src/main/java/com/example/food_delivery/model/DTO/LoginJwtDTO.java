package com.example.food_delivery.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginJwtDTO {

    private String userName;

    private String role;

    private String accessToken;

    private String tokenType = "Bearer";

    public LoginJwtDTO(String userName, String role, String accessToken) {
        this.userName = userName;
        this.role = role;
        this.accessToken = accessToken;
    }
}
