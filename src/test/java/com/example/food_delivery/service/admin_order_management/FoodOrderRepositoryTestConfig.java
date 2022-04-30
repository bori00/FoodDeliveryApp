package com.example.food_delivery.service.admin_order_management;

import com.example.food_delivery.repository.FoodOrderRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class FoodOrderRepositoryTestConfig {
    @Bean
    @Primary
    public FoodOrderRepository orderRepository() {
        return Mockito.mock(FoodOrderRepository.class);
    }
}
