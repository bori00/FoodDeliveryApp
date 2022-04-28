package com.example.food_delivery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Entity
@Table(name = "food_order")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FoodOrder {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "Id")
    private Customer customer;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "Id")
    private Restaurant restaurant;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "foodOrder", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems;

    public FoodOrder(OrderStatus orderStatus, Customer customer, Restaurant restaurant,
                     LocalDateTime localDateTime) {
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.restaurant = restaurant;
        this.dateTime = localDateTime;
        this.orderItems = new HashSet<>();
    }

    public double getTotalPrice() {
        return orderItems.stream().mapToDouble(orderItem -> orderItem.getFood().getPrice() * orderItem.getQuantity()).sum();
    }

    public enum OrderStatus {
        PENDING("P"),
        ACCEPTED("A"),
        IN_DELIVERY("ID"),
        DELIVERED("F"),
        DECLINED("X");

        private final String code;

        OrderStatus(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        @Converter(autoApply = true)
        public static class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

            @Override
            public String convertToDatabaseColumn(OrderStatus OrderStatus) {
                if (OrderStatus == null) {
                    return null;
                }
                return OrderStatus.getCode();
            }

            @Override
            public OrderStatus convertToEntityAttribute(String code) {
                if (code == null) {
                    return null;
                }

                return Stream.of(OrderStatus.values())
                        .filter(c -> c.getCode().equals(code))
                        .findFirst()
                        .orElseThrow(IllegalArgumentException::new);
            }
        }
    }
}