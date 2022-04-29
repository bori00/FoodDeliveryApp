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

/**
 * Represents an order placed by a customer to a given restaurant.
 */
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

    /** The timestamp at which the order was placed. */
    @Column(nullable = false)
    private LocalDateTime dateTime;

    /** The set of ordered items. */
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

    /**
     * @return the total price of the ordered items.
     */
    public double getTotalPrice() {
        return orderItems.stream().mapToDouble(orderItem -> orderItem.getFood().getPrice() * orderItem.getQuantity()).sum();
    }

    /**
     * Represents the status of an order.
     */
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

        /**
         * @return the code of the status, for persistence in the database.
         */
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