package goorm.bookstore.inventory.dto;

import goorm.bookstore.cart.domain.Cart;
import goorm.bookstore.inventory.domain.Inventory;
import goorm.bookstore.inventory.domain.InventoryStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class InventoryForUserDto {


    private Long inventoryId;
    private String title;
    //private String isbn;
    private String contents; // 소개내용 추가
    private LocalDateTime dateTime; // 출판일 추가
    private String translators; // 번역가 추가
    private String authors;
    private String publisher;
    private int salePrice;
    private String thumbnail;
    private InventoryStatus status;
    private int quantity;

    public static InventoryForUserDto InventoryForUserDtoFromCart(Cart cart) {
        return Inventory.getInventoryUserDto(cart.getInventory());
    }
}
