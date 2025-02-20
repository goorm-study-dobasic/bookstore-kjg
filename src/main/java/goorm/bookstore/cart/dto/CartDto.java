package goorm.bookstore.cart.dto;

import goorm.bookstore.inventory.dto.InventoryForUserDto;

public class CartDto {
    // 고객의 장바구니 페이지에서 상품 리스트 출력 용도
    private Long cartId;
    private InventoryForUserDto inventoryForUserDto;

}
