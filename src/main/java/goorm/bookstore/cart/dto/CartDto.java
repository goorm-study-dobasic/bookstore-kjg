package goorm.bookstore.cart.dto;

import goorm.bookstore.cart.domain.Cart;
import goorm.bookstore.inventory.dto.InventoryForUserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartDto {
    // 고객의 장바구니 페이지에서 상품 리스트 출력 용도
    private Long cartId;
    private InventoryForUserDto inventoryForUserDto;
    int quantity;

    public CartDto() {
    }

    public static CartDto createCartDtoFromCart(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setInventoryForUserDto(InventoryForUserDto.InventoryForUserDtoFromCart(cart));
        cartDto.setCartId(cart.getId());
        cartDto.setQuantity(cart.getQuantity());
        return cartDto;
    }
}
