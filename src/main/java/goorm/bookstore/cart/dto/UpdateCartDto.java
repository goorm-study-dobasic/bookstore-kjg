package goorm.bookstore.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartDto {

    private Long cartId;

    private int quantity;
}
