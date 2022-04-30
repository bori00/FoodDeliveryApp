package com.example.food_delivery.service.admin_order_management;

import com.example.food_delivery.service.authentication.AuthenticationService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class AuthenticationServiceTestConfig {
    @Bean
    @Primary
    public AuthenticationService authenticationService() {
        return Mockito.mock(AuthenticationService.class);
    }
}
