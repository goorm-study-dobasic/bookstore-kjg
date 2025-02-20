package goorm.bookstore.orderItem.dto;

import goorm.bookstore.inventory.dto.InventoryForUserDto;

import java.time.LocalDateTime;

public class OrderItemDto {
    private Long orderItemId;
    private InventoryForUserDto inventoryForUserDto;
    private int quantity;
    private int price;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
}
