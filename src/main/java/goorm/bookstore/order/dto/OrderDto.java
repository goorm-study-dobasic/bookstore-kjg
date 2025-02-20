package goorm.bookstore.order.dto;

import goorm.bookstore.order.domain.OrderStatus;

import java.time.LocalDateTime;

public class OrderDto {
    private Long orderId;
    private OrderStatus status;
    private LocalDateTime createdAt;
}
